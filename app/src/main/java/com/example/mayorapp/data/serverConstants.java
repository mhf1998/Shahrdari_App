package com.example.mayorapp.data;

public class serverConstants {
    public static final String ROOT_URL = "http://192.168.1.4/API/";
    public static final String Register_URL = ROOT_URL+"insertUser.php";                      //post
    public static final String SignIn_URL = "userAuth.php";                          //get
    public static final String addSurvey2op_URL = "insertVote.php";                  //post
    public static final String addSurvey4op_URL = "insertVote4Op.php";               //post
    public static final String addSurvey2opPIC_URL ="insertVoteWithPic.php";        //post
    public static final String addSurvey4opPIC_URL ="insertVoteWithPic4.php";       //post
    public static final String deleteSurvey_URL = ROOT_URL+"deleteVote.php";                  //get
    public static final String getMySurvey_URL = ROOT_URL+"getMySurvey.php";                  //get
    public static final String getSurvey_URL = ROOT_URL+"getSurvey.php";                      //get
    public static final String userInVote_URL = ROOT_URL+"userInVote.php";                    //get
    public static final String getSurveyDetail_URL = ROOT_URL+"getVoteDetail.php";            //get
    public static final String voting_URL = ROOT_URL+"voting.php";                            //get
    public static final String getAllNews_URL = ROOT_URL+"getNews.php";                       //get
    public static final String increaseNewsSeenCount_URL = ROOT_URL+"increaseSeenCount.php";  //post
    public static final String getUsers ="getUsers.php";
    public static final String getUser = ROOT_URL+"getUser.php";
    public static final String blockUser = ROOT_URL+"blockUser.php";                           //get
    public static final String activeUser = ROOT_URL+"activeUser.php";                         //get
    public static final String insertNews ="insertNews.php";                         //post
    public static final String getNewsFromTo ="getNewsFromTo.php";                    //get
    public static final String  getSurveyFromTo ="getSurveyFromTo.php";              //get
    public static final String  getVoteOptionParticipate ="getVoteOptionParticipate.php";   //get




}
