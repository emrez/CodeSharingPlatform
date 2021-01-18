<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create</title>
</head>
<body>
<div>
    <input id="time_restriction" type="text"/>
    <input id="views_restriction" type="text"/>
    <textarea id="code_snippet">// write your code here</textarea>
    <button id="send_snippet" type="submit" onclick="send()">Submit</button>
    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value,
                "time": Number(document.getElementById("time_restriction").value),
                "views": Number(document.getElementById("views_restriction").value)
            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status == 200) {
                alert("Success!");
            }
        }
    </script>
</div>
</body>
</html>