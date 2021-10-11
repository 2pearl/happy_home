package com.example.happy_home;

import java.util.HashMap;
import java.util.Map;

public class Board {

    protected int post_num;
    protected String Time ; //작성시간
    protected String Title; //글의 제목
    protected String Writer;    //글의 작성자 닉네임으로 할꺼얌
    protected String Text; //글의 내용을 저장해 줄 것이다.

    public Board(){}
    public Board(int post_num,String Time, String Title, String Writer, String Text){
          this.post_num=post_num;
        this.Time = Time;
        this.Title = Title;
        this.Writer = Writer;
        this.Text = Text;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("post_num",post_num);
        result.put("Time",Time);
        result.put("Title",Title);
        result.put("Writer",Writer);
        result.put("Text",Text);

        return result;

    }
}
