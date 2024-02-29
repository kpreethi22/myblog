package com.my.blog.my.blog.payload;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@ApiModel(description = "Post model information")
@Data
public class PostDTO {
    private long id;
    @NotEmpty
    @Size(min = 4, message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    @Size(min = 2, message = "Post content should not be empty")
    private String content;

    private Set<CommentDto> comments;
}
