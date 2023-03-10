the content of the input file doesn't contain anything unexpected

it contains only integers, spaces and newline characters

it is not empty has at least a single row

the structure conforms to what is described in the document. i.e. 

the amount of values in each row is n + 1 where n is the rows index (0 based)

values are separated by a single space

rows are separated by a single newline character

---

justification

all the above can be justified (maybe except some whitespace assumption) by the fact that if these assumptions 
can't be relied on, e.g. there is a 'B' ccharacter in the triangle or the row sizes are incorrect, 
no result can be calculated that would be meaningful given the requirements.