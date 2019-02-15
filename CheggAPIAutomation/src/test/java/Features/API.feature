Feature: Verify JSON Blob API for POST, GET, UPDATE and DELETE methods

  Scenario: Verify that user is able to create a new blob
    Given ContentType header is set to JSON
    When User post the blob payload
    Then New resource is created

#  Scenario: Verify that the new resource created can be retrieved
#    Given New blob is created
#    When User perform get on the new resource created
#    Then Server respond with the 200 response
#
#  Scenario: Verify that user is able to update the existing blob
#    Given New blob is created
#    When User update the existing blob
#    Then Existing blog is updated with new information
#
#  Scenario: Verify that the blob can be deleted
#    Given New blog is created
#    When User perform Delete operation on the blob
#    Then Blob is deleted succesfully