package com.stone.springbootrestblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private List<CommentDto> commentsList;
    private long noOfComments;
    private long noOfPages;
    private long pageNo;
    private long pageSize;
    private boolean isLast;
}
