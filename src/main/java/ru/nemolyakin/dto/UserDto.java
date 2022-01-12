package ru.nemolyakin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nemolyakin.model.User;
import ru.nemolyakin.model.UserStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends BaseDtoEntity {

    private String firstName;
    private String lastName;
    private String username;
    private LocalDateTime created;
    private List<AnswerDto> answers = new ArrayList<>();
    private LocalDateTime updated;
    private UserStatus userStatus;

    public UserDto(@NotNull User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.created = user.getCreated();
        this.answers = user.getAnswers().stream().map(AnswerDto::new).collect(Collectors.toList());
        this.updated = user.getUpdated();
        this.userStatus = user.getUserStatus();
    }

    public User toUser() {
        User user = new User();

        user.setId(this.id);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setCreated(this.created);
        user.setAnswers(this.answers.stream().map(AnswerDto::toAnswer).collect(Collectors.toList()));
        user.setUpdated(this.updated);

        return user;
    }

}
