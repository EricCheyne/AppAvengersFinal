import  express  from 'express';
import { supabase } from './supabase.js';
import bodyParser from 'body-parser';
var app = express();

app.use(bodyParser.json())

app.post('/api/charges', async function(request, response) {
    // get all data from request
    const name = request.body.name
    const value = parseFloat(request.body.value)
    const type = request.body.type

    // add value to db
    const { error } = await supabase
        .from('charges')
        .insert({name: name, value: value, type: type})
    if (error) {
        // if error retrun error
        response.send(error)
    } else {
        // return success on success
        response.send("Success")
    }
})

var server = app.listen(8081, function () {
   var port = server.address().port
   
   console.log("Example app listening at localhost:", port)
})