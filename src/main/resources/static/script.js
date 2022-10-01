
        var btn = document.getElementById("forum");
        btn.addEventListener("click", forumAlert);
        function forumAlert() {
            var result= confirm("Please sign in before use forum");
            if(result== false){
                event.preventDefault();
            }
        }

        var btn1 = document.getElementById("adminpanel");
        btn1.addEventListener("click", adminPanelAlert);
        function adminPanelAlert() {
             var result= confirm("You cannot log in unless you are an admin");
             if(result== false){
                    event.preventDefault();
             }

        }

        //File approve confirmation
        function approveChecker() {
             var result = confirm("Are you Sure? This file will be visible to the public");
             if(result == false){
                 event.preventDefault();
             }
        }

        //File reject confirmation
        function rejectChecker() {
            var result = confirm("Are you Sure? This file will be remove from this list");
             if(result == false){
                 event.preventDefault();
             }
        }

         //User remove confirmation
         function approveDeleteUser() {
              var result= confirm("Are you Sure? This user will be deleted from the system.This particular users asked questions and given answers also will be deleted.This cannot be undone!");
              if(result == false){
                 event.preventDefault();
              }
         }

         function changeRole() {
              var result= confirm("Are you Sure? This user's role will be changed ");
              if(result == false){
                 event.preventDefault();
              }
         }


         //Password hide and unhide
         function hideUnhideFunction(){

                 var x = document.getElementById("myInput");
                 var y = document.getElementById("hide1");
                 var z = document.getElementById("hide2");

                 if(x.type == 'password'){
                     x.type = "text"
                     y.style.display = "block";
                     z.style.display = "none";

                 }else{
                     x.type = "password"
                     y.style.display = "none";
                     z.style.display = "block";

                 }

         }



         function vote(answerId,rateValue){

            axios.post('/saveAndDisplayRatings?answerId=' + answerId + '&rateValue=' + rateValue, {

             })
            .then(function(response){

                const updatedRatingMap = new Map(Object.entries(response.data.rateUpdatedValue));
                const updatedPositiveRatingMap = new Map(Object.entries(response.data.rateUpdatedPositiveValue));
                const updatedNegativeRatingMap = new Map(Object.entries(response.data.rateUpdatedNegativeValue));

                 updatedRatingMap.forEach((value,key) => {

                    console.log(value,key);
                    //id="13answer_countId"
                    $('#'+ key + 'answer_countId').html(value);

                 });

                updatedPositiveRatingMap.forEach((value,key) => {

                     $('#'+ key + 'answer_positiveCountId').html(value);

                });

                 updatedNegativeRatingMap.forEach((value,key) => {

                      $('#'+ key + 'answer_negativeCountId').html(value);

                 });

            })

             axios.post('/changeRatingButtonsAccordingToClick?answerId=' + answerId , {

             })
              .then(function(response){

                     if(response.data == rateValue){

                           $('#'+ answerId + 'answer_upVoteId').css("background-color","");
                           $('#'+ answerId + 'answer_downVoteId').css("background-color","");

                     }else if(response.data == 0){

                           if(rateValue == 1){

                                   $('#'+ answerId + 'answer_upVoteId').css("background-color","green");
                                   $('#'+ answerId + 'answer_downVoteId').css("background-color","")

                           }else if(rateValue == -1){

                                   $('#'+ answerId + 'answer_downVoteId').css("background-color","red");
                                   $('#'+ answerId + 'answer_upVoteId').css("background-color","")

                           }


                     }else if(response.data != rateValue){

                          if(rateValue == 1){

                                  $('#'+ answerId + 'answer_upVoteId').css("background-color","green");
                                  $('#'+ answerId + 'answer_downVoteId').css("background-color","");

                          }else{

                                  $('#'+ answerId + 'answer_upVoteId').css("background-color","");
                                  $('#'+ answerId + 'answer_downVoteId').css("background-color","red");

                          }

                     }

              })

         }


         function loadForum(){

               //alert("FORUM IS LOADING");

                var answerIdArray = [];

                $(".answer_header").each(function() {

                        //console.log(this.id);
                        answerIdArray.push(this.id);
                });

                axios.post('/loadRatingValues?answerIdArray=' + answerIdArray , {

                })
                .then(function(response){

                           // alert(response.data)

                           const answerRatingMap = new Map(Object.entries(response.data.answerIdRatingCount));
                           const answerPositiveRatingMap = new Map(Object.entries(response.data.answerIdRatingPositiveCount));
                           const answerNegativeRatingMap = new Map(Object.entries(response.data.answerIdRatingNegativeCount));


                            //Answer Ratings Display when forum is loading
                            answerRatingMap.forEach((value,key) => {

                                console.log(value,key);
                                //id="13answer_countId"
                                $('#'+ key + 'answer_countId').html(value);

                            });

                            answerPositiveRatingMap.forEach((value,key) => {

                              $('#'+ key + 'answer_positiveCountId').html(value);

                            });

                            answerNegativeRatingMap.forEach((value,key) => {

                               $('#'+ key + 'answer_negativeCountId').html(value);

                            });

                })


                 axios.post('/loadVotingButtons?answerIdArray=' + answerIdArray , {

                 })
                 .then(function(response){

                     //Logged in user's rating buttons colors display

                     const usersRatingButtonMap = new Map(Object.entries(response.data.specificUsersRatingValue));

                     usersRatingButtonMap.forEach((value,key) => {

                          if(value == 1){

                             $('#'+ key + 'answer_upVoteId').css("background-color","green");
                             $('#'+ key + 'answer_downVoteId').css("background-color","");


                          }else if(value == null) {

                              $('#'+ key + 'answer_downVoteId').css("background-color","");
                              $('#'+ key + 'answer_upVoteId').css("background-color","");

                          }else{

                              $('#'+ key + 'answer_downVoteId').css("background-color","red");
                              $('#'+ key + 'answer_upVoteId').css("background-color","");

                          }
                     });



                     /*Hide answer authors rating buttons from their own answers*/

                     /*Convert object to array*/
                     const loggedUsersAnswerIdsArray = Object.values(response.data.answerIds);

                     for(var i in loggedUsersAnswerIdsArray){

                         $('#'+ loggedUsersAnswerIdsArray[i] + 'answer_upVoteId').hide();
                         $('#'+ loggedUsersAnswerIdsArray[i] + 'answer_downVoteId').hide();
                         $('#'+ loggedUsersAnswerIdsArray[i] + 'answer_rateParaId').hide();

                     }


                 })

                /*Hide or show delete and accept answer button according to the user id when loading */
                 axios.post('/loadDeleteAcceptAnswerButtons?answerIdArray=' + answerIdArray , {

                  })
                  .then(function(response){

                         const userIdAnswerIdMap = new Map(Object.entries(response.data.answerUserId));

                         userIdAnswerIdMap.forEach((value,key) => {

                              if(response.data.currentLoggedInUserId!= value && response.data.userRole == "USER"){

                                       $('#'+ key + 'answer_deleteBtn').hide();
                                       //$('#'+ key + 'answer_acceptBtn').hide();

                              }else if (response.data.currentLoggedInUserId == value){

                                     $('#'+ key + 'answer_acceptBtn').hide();

                              }

                         })


                         const answerIdQuestionAuthorIdMap = new Map(Object.entries(response.data.answerIdQuestionAuthorId));

                         answerIdQuestionAuthorIdMap.forEach((value,key) => {

                                          if(response.data.currentLoggedInUserId!= value){

                                                 $('#'+ key + 'answer_acceptBtn').hide();


                                          }else if (response.data.currentLoggedInUserId==value){


                                           }

                         })

                  })

                 var questionIdArray = [];

                 $(".question_header").each(function() {

                        questionIdArray.push(this.id);

                 });

                 //Question status Solved or not solved on the top of the question when forum is loading, delete button hide and show and question edit button show or hide
                 axios.post('/loadQuestionStatus?questionIdArray=' + questionIdArray , {

                 })
                 .then(function(response){

                             //alert(response.data.currentLoggedInUserId);

                             const questionIdStatusMap = new Map(Object.entries(response.data.questionIdQuestionStatusValue ));

                              //Question status Solved or not solved on the top of the question when forum is loading
                             questionIdStatusMap.forEach((value,key) => {

                                   $('#'+ key + 'question_status' ).html(value);

                             });

                              const questionIdUserIdMap = new Map(Object.entries(response.data.questionIdWithUserId ));

                             //Question delete button and Edit buttons are only available for question authors and admin users
                             questionIdUserIdMap.forEach((value,key) => {

                                       if(response.data.currentLoggedInUserId != value && response.data.userRole == "USER" ){

                                           $('#'+ key + 'question_deleteBtn').hide();
                                           $('#'+ key + 'question_editBtn').hide();

                                       }else if(response.data.UserRole == "ADMIN"){

                                           $('#'+ key + 'question_deleteBtn').show();
                                           $('#'+ key + 'question_editBtn').show();
                                       }
                             });


                 })

                  var answerIdArray = [];

                  $(".answer_header").each(function() {

                          answerIdArray.push(this.id);

                  });

                 axios.post('/loadAnswerAcceptBtn?answerIdArray=' + answerIdArray , {

                 })
                  .then(function(response){

                            const answerIdAnswerStatusMap = new Map(Object.entries(response.data.answerIdAnswerStatus));

                            answerIdAnswerStatusMap.forEach((value,key) => {

                                 if(value == "Answer Accepted"){

                                     $('#'+ key + 'answer_acceptBtn').css("background-color","green");
                                     $('#'+ key + 'answer_acceptBtn').html("Answer Accepted");

                                     $('#'+ key + 'answer_acceptArea').html("Accepted Answer");

                                 }else if(value == "Not Accepted"){

                                     $('#'+ key + 'answer_acceptBtn').css("background-color","yellow");
                                     $('#'+ key + 'answer_acceptBtn').html("Accept This answer");

                                 }

                            });

                  })

                  /*Report question button loading when forum is loading*/
                  axios.post('/loadQuestionReportButton?questionIdArray=' + questionIdArray , {

                   })
                   .then(function(response){

                                           const questionIdReportStatusMap = new Map(Object.entries(response.data.questionIdWithReportStatus));

                                           questionIdReportStatusMap.forEach((value,key) => {

                                              if(response.data.loggedInUsersRole == "ADMIN"){

                                                  $('#'+ key + 'question_reportBtn').hide();
                                              }

                                              else if(value == null){

                                                  $('#'+ key + 'question_reportBtn').css("background-color","green");
                                                  $('#'+ key + 'question_reportBtn').html("Report Question");

                                              }else if(value == "REPORTED"){

                                                   $('#'+ key + 'question_reportBtn').css("background-color","red");
                                                   $('#'+ key + 'question_reportBtn').html("Question Reported");

                                              }else if(value == "NOTREPORTED"){

                                                  $('#'+ key + 'question_reportBtn').css("background-color","green");
                                                  $('#'+ key + 'question_reportBtn').html("Report Question");

                                              }

                                           });



                                            /*Hide report button from logged in users own questions*/
                                            /*Convert object to array*/
                                            const loggedUsersQuestionIdsArray = Object.values(response.data.loggedUsersQuestionIds);

                                            for(var i in loggedUsersQuestionIdsArray){

                                                  $('#'+ loggedUsersQuestionIdsArray[i] + 'question_reportBtn').hide();

                                            }


                   })



                    /*Report answer button loading when forum is loading*/
                    axios.post('/loadAnswerReportButton?answerIdArray=' + answerIdArray , {

                    })
                    .then(function(response){

                                           const answerIdReportStatusMap = new Map(Object.entries(response.data.answerIdWithReportStatus));

                                           answerIdReportStatusMap.forEach((value,key) => {

                                                                if(response.data.loggedInUsersRole == "ADMIN"){

                                                                     $('#'+ key + 'answer_reportBtn').hide();
                                                                }

                                                                 else if(value == null){

                                                                     $('#'+ key + 'answer_reportBtn').css("background-color","green");
                                                                     $('#'+ key + 'answer_reportBtn').html("Report Answer");

                                                                 }else if(value == "REPORTED"){

                                                                      $('#'+ key + 'answer_reportBtn').css("background-color","red");
                                                                      $('#'+ key + 'answer_reportBtn').html("Answer Reported");

                                                                 }else if(value == "NOTREPORTED"){

                                                                     $('#'+ key + 'answer_reportBtn').css("background-color","green");
                                                                     $('#'+ key + 'answer_reportBtn').html("Report Answer");

                                                                 }
                                           });

                                           /*Hide report button from logged in users own answers*/
                                           /*Convert object to array*/
                                           const loggedUsersAnswerIdsArray = Object.values(response.data.loggedUsersAnswerIds);

                                           for(var i in loggedUsersAnswerIdsArray){

                                              $('#'+ loggedUsersAnswerIdsArray[i] + 'answer_reportBtn').hide();

                                           }

                    })

                     /*Show reported answer when admin checks reported answers*/
                     axios.post('/loadAnswerReportStatusForAdmins' , {

                     })
                     .then(function(response){

                                           const answerIdReportStatusMap = new Map(Object.entries(response.data.answerIdWithReportStatus));

                                          /*Show reported answer when admin checks reported answers*/
                                           answerIdReportStatusMap.forEach((value,key) => {

                                               if(response.data.loggedInUsersRole != "ADMIN"){

                                                     $('#'+ key + 'answer_statusForAdmins').html("");

                                               }else{

                                                     if(value == "REPORTED"){

                                                          $('#'+ key + 'answer_statusForAdmins').html("Users have reported this answer");

                                                     }

                                               }

                                           });

                     })

         }

         /*notifications count indication*/
         function loadNotificationPanel(){

             axios.post('/loggedInUserNotificationPanelCheckedTime',  {

             })
             .then(function(response){

             })
         }



         function loadHomePage(){

                                    axios.post('/hideSignUpButtonFromRegisteredUsers',  {

                                    })
                                    .then(function(response){

                                                  if(response.data != 0 ){

                                                     $('#'+'signUpBtn').hide();
                                                     document.getElementById("logOutBtn").hidden = false;

                                                  }else if(response.data == 0 ){

                                                      $('#'+'signUpBtn').show();
                                                      $('#'+'logOutBtn').hide();

                                                  }

                                    })


         }


         function loadTamilMedium(){

                                   axios.post('/loadTamilMedium',  {

                                   })
                                   .then(function(response){

                                                 if(response.data != 0 ){

                                                    $('#'+'signUpBtn').hide();
                                                    document.getElementById("logOutBtn").hidden = false;

                                                 }else if(response.data == 0 ){

                                                     $('#'+'signUpBtn').show();
                                                     $('#'+'logOutBtn').hide();

                                                 }

                                   })


         }

          function loadEnglishMedium(){

                                    axios.post('/loadEnglishMedium',  {

                                    })
                                    .then(function(response){

                                                          if(response.data != 0 ){

                                                             $('#'+'signUpBtn').hide();
                                                             document.getElementById("logOutBtn").hidden = false;

                                                          }else if(response.data == 0 ){

                                                              $('#'+'signUpBtn').show();
                                                              $('#'+'logOutBtn').hide();

                                                          }

                                    })


          }

          function loadSinhalaMedium(){

                                    axios.post('/loadSinhalaMedium',  {

                                    })
                                    .then(function(response){

                                                  if(response.data != 0 ){

                                                     $('#'+'signUpBtn').hide();
                                                     document.getElementById("logOutBtn").hidden = false;

                                                  }else if(response.data == 0 ){

                                                      $('#'+'signUpBtn').show();
                                                      $('#'+'logOutBtn').hide();

                                                  }

                                    })


          }


          function loadAdminPanel(){

                    axios.post('/loadAdminPanel'  , {

                    })
                    .then(function(response){

                            $('#'+'numberOfPendingFiles').html(response.data.numberOfPendingFiles);
                            $('#'+'numberOfReportedQuestions').html(response.data.numberOfReportedQuestions);
                            $('#'+'numberOfReportedAnswers').html(response.data.numberOfReportedAnswers);

                    })

          }
          function loadProfile(viewedUserId){

               axios.post('/loadProfile?viewedUserId=' + viewedUserId , {

               })
               .then(function(response){

                    if(response.data == "Matched"){

                         $('#' + 'profileEditButton').show();


                    }else if(response.data == "NotMatched"){

                         $('#'+ 'profileEditButton').hide();

                    }


               })

          }




















         /*Onclick functions*/


        function showContribution(questionAuthorName){

              axios.post('/showContribution?questionAuthorName=' + questionAuthorName , {

              })
              .then(function(response){

                  var upVotePercentage = (response.data.numberOfUpVotes / (response.data.numberOfUpVotes + response.data.numberOfDownVotes) ) *100;
                  var downVotePercentage = (response.data.numberOfDownVotes / (response.data.numberOfUpVotes + response.data.numberOfDownVotes) ) *100;

                  alert("User Name " + questionAuthorName  + "\n" + "User role " + response.data.role  + "\n" +"Joined date " + response.data.joinedDateAndTime + "\n" + "\n" + "Number of questions asked " + response.data.numberOfQuestionAsked +"\n" + "Number of answers given "+ response.data.numberOfAnswersGiven +"\n" + "Number of Green star votes received " + response.data.numberOfUpVotes +"\n" + "Number of Red star votes received " + response.data.numberOfDownVotes +"\n" +"\n" +"Number of accepted answers " + response.data.numberOfAcceptedAnswers + "\n"  + "Green star vote percentage " + upVotePercentage +"%"+ "\n" + "Red star vote percentage " + downVotePercentage +"%");

              })

        }

        function previouslyAskedQuestions(){

              $('#' + 'forum-header').html("previouslyAskedQuestions");

        }



        /*Report question button change */
        function reportQuestion(questionId){

               axios.post('/reportQuestion?questionId=' + questionId ,  {

               })
               .then(function(response){

                  if(response.data.questionReportedOrNot == null){

                                                  $('#'+ questionId + 'question_reportBtn').css("background-color","green");
                                                  $('#'+ questionId + 'question_reportBtn').html("Report Question");

                  }else if(response.data.questionReportedOrNot == "REPORTED"){

                                                  $('#'+ questionId + 'question_reportBtn').css("background-color","red");
                                                  $('#'+ questionId + 'question_reportBtn').html("Question Reported");

                  }else if(response.data.questionReportedOrNot == "NOTREPORTED"){

                                                   $('#'+ questionId + 'question_reportBtn').css("background-color","green");
                                                   $('#'+ questionId + 'question_reportBtn').html("Report Question");

                  }

               })

        }

         /*Report answer button change */
         function reportAnswer(answerId){

                       axios.post('/reportAnswer?answerId=' + answerId ,  {

                       })
                       .then(function(response){

                           if(response.data.answerReportedOrNot == null){

                                   $('#'+ answerId + 'answer_reportBtn').css("background-color","green");
                                   $('#'+ answerId + 'answer_reportBtn').html("Report Answer");

                           }else if(response.data.answerReportedOrNot == "REPORTED"){

                                    $('#'+ answerId+ 'answer_reportBtn').css("background-color","red");
                                    $('#'+ answerId + 'answer_reportBtn').html("Answer Reported");

                           }else if(response.data.answerReportedOrNot == "NOTREPORTED"){

                                    $('#'+ answerId + 'answer_reportBtn').css("background-color","green");
                                    $('#'+ answerId + 'answer_reportBtn').html("Report Answer");

                           }

                       })

         }

       /*function editQuestions(questionId){

              axios.post('/editQuestions?questionId=' + questionId, {

               })
               .then(function(response){



                       window.location.reload(true);

               })




       }*/

       //when question author trying to delete the question or admin user delete the question panel this alert will display
       function deleteQuestion(questionId){

                  var result= confirm("Are you Sure? This question will be deleted.This cannot be undone!");

                  if(result == false){
                      event.preventDefault();
                  }
                  else{

                           axios.post('/deleteQuestion?questionId=' + questionId , {

                           })
                           .then(function(response){

                                        window.location.reload(true)

                                        console.log("Axios post");

                           })
                  }

       }


        //when answer author trying to delete the question or admin user delete the answer panel this alert will display
        function deleteAnswer(answerId){

                    var result= confirm("Are you Sure? This answer will be deleted.This cannot be undone!");

                    if(result == false){
                       event.preventDefault();
                    }
                    else{

                           axios.post('/deleteAnswer?answerId=' + answerId , {

                           })
                           .then(function(response){

                                  window.location.reload(true)

                                  console.log("Axios post");

                           })
                    }

        }




        /*Accept the answer and Indicate question status on the top of the question realtime not when loading the forum*/
         function acceptAnswer(answerId){
                //alert(answerId);

                 axios.post('/acceptAnswer?answerId=' + answerId , {

                  })
                  .then(function(response){

                         const question_IdStatusMap = new Map(Object.entries(response.data.questionIdStatusValue ));

                         question_IdStatusMap.forEach((value,key) => {

                              //alert(value);
                              //alert(key);

                              if(value == "UNSOLVED"){

                                        $('#'+ key + 'question_status' ).html(value);

                              }else if (value == "SOLVED") {

                                        $('#'+ key + 'question_status').html(value);

                              }

                             //$('#'+ key ).html(value);
                         });

                  })



                  axios.post('/acceptAnswerRealTimeDisplay?answerId=' + answerId , {

                  })
                  .then(function(response){

                    const answerStatusQuestionStatusMap = new Map(Object.entries(response.data.answerStatusQuestionStatus));

                    answerStatusQuestionStatusMap.forEach((value,key) => {

                      if(value == "UNSOLVED"){

                                             $('#'+ answerId + 'answer_acceptBtn').html("Answer Accepted");
                                             $('#'+ answerId + 'answer_acceptBtn').css("background-color","green");

                                             $('#'+ answerId + 'answer_acceptArea').html("Accepted Answer");

                      }else if (value == "SOLVED"){

                                                if(key == "Not Accepted"){

                                                    $('#'+ answerId + 'answer_acceptBtn').html("Answer Accepted");
                                                    $('#'+ answerId + 'answer_acceptBtn').css("background-color","green");

                                                }else if(key == "Answer Accepted"){

                                                     $('#'+ answerId + 'answer_acceptBtn').html("Accept This answer");
                                                     $('#'+ answerId + 'answer_acceptBtn').css("background-color","yellow");
                                                     $('#'+ answerId + 'answer_acceptBtn').css("color","black");

                                                     $('#'+ answerId + 'answer_acceptArea').html("");

                                                }


                      }

                    });

                    /* if(response.data == "Answer Accepted"){

                     }else if(response.data == "Not Accepted"){

                           $('#'+ answerId + 'answer_acceptBtn').html("Answer Accepted");
                           $('#'+ answerId + 'answer_acceptBtn').css("background-color","green");


                     }*/
                  })

         }







         function changeCommentSectionButtonText(){

            //alert("Comment button");

            /* var elem = document.getElementById("commentSection");
             if (elem.value=="Show comment sections") elem.value = "Hide comment sections";
             else elem.value = "Show comment sections";*/


         }



         /* var btnTest = document.getElementById("btncolor");

                   btnTest.addEventListener("click", function changeColorButton() {
                   btnTest.style.backgroundColor = "green";
                   btnTest.style.color = 'white';
         });*/


         /*function changeColor(){
           var property = document.getElementById("btncolor");
           property.style.backgroundColor = "Pink";
          alert("Isuru Kotta");
         }*/



         /*function yourJsFunction(){

                alert("Working");
         }
*/
         /*function answerSubmitTest(qid){

              *//*alert("Answer submitted");
               window.location.hash = $("#qid");
               window.location.reload(true);*//*

               const element = document.getElementById("qid");
                element.scrollIntoView();
         }*/

       /*  function iKTest(str){

            alert(str);
            var id = str;

          *//*  var elem = document.getElementById("str + 'questionidload");
             elem.scrollIntoView();*//*

             window.location.href = forum.html;

             var myDivID = "#" + id + "questionidload";
             window.location.hash = myDivID;


         }*/





























