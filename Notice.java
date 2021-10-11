package com.example.happy_home;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Notice extends Board implements Serializable {

    Notice(int post_num,String Time, String Title, String Writer, String Text){
        super(post_num,Time,Title,Writer,Text);

        this.post_num = post_num;
        this.Time = Time;
        this.Title = Title;
        this.Writer = Writer;
        this.Text = Text;

    }



    public int getPostNum(){return post_num;}
    public String getTitle(){return Title;}
    public String getText(){return Text;}
    public String getWriter(){return Writer;}
    public String getTime(){return Time;}

}