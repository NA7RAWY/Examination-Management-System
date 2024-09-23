package com.mycompany.pl2_project;


public class Subject {
    
    private String subjectName;
    private String subjectCode;
    public Subject(String subjectName,String subjectCode){
        this.subjectCode=subjectCode;
        this.subjectName=subjectName;
    }
    
    public void setSubjectName(String subjectName){
    this.subjectName = subjectName;
    }
    
    public String getSubjectName(){
    return subjectName;
    }
    
    public void setSubjectCode(String subjectCode){
    this.subjectCode = subjectCode;
    }
    
    public String getSubjectCode(){
    return subjectCode;
    }
}
