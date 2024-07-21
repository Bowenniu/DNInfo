# Domain Name Information  Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram

Place your class diagram below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

```mermaid
---
title: DNInfoApp Design
---
classDiagram
    class DNInfoApp {
        +main(String[] args)
    }
    class ArgsController {
        -DomainNameModel model
        -Formats format
        -OutputStream output
        -String hostname
        -String dataFilePath
        +setFormat(String formatStr)
        +setOutputPath(String outputPath)
        +setDataFilePath(String dataFilePath)
        +processHostname(String hostname)
        +getHelp() : String
    }
    class DomainNameModel {
        +getRecords() : List<DNRecord>
        +getRecord(String hostname) : DNRecord 
        +writeRecords(List<DNRecord> records, Formats format, OutputStream out)
        +getInstance() : DomainNameModel
        +getInstance(String database) : DomainNameModel
    }
    class DNRecord {
        -String hostname
        -String ip
        -String city
        -String region
        -String country
        -String postal code
        -String latitude
        -String longitude
    } 
    class DataFormatter {
        +prettyPrint(Collection<DNR> records, OutputStream out)
        +prettySingle(DNRecord record, PrintStream out)
        +writeXmlData(Collection<DNRecord> records, OutputStream out)
        +writeJsonData(Collection<DNRecord> records, OutputStream out)
        +writeCSVData(Collection<DNRecord> records, OutputStream out)
        +write(Collection<DNRecord> records, Formats format, OutputStream out)
    }
    class Formats {
        <<emum>>
        PRETTY
        XML
        JSON
        CSV
    }
    class DomainXmlWrapper {
        +wrap(List<DNRecord> records) : String
        +unwrap(String xml) : List<DNRecord>
    }
```

## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test the controller if it can correctly putput the expected result 
2. Test the output format as JSON
3. Test if the function can display --help information




## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the fil in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

Initially, my design was based on a broad and idealized structure, but as i delved deeper into coding and faced practical constrains, significant modifications became inevitable. One major change involved adjusting the DomainNameModel class to better integrate with the ArgsController and DataFormatter classes. Initially, the data handling in the DomainNameModel was designed in a simplistic manner, but as the need for different output formats became clearer, I realized a more flexible design was required. This led to the introduction of additional methods and an optimized class structure to support XML, JSON, and CSV formats. The other significant change occured in the handling of command-line arguments. My initial design had a rudimentary parameter processing mechanism that did not account for all edge cases and error scenarios. As I run the test, I realized the importance of robust argument validation and error handling, which prompted me to significantly improve the argument parsing logic.
The most challenging part in the process was integrating the various components together while ensuring they collaborated seamlessly. Balancing the need for scalability, performance, and user input processing was complex and required careful consideration and frequent adjustments. Despite these challenges, this experience underscored the value of a flexible design approach and the importance of interative development in creating a robust and adaptable system.