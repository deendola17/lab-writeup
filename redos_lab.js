const readline = require('readline');
// The "Evil" Regex: ([a-zA-Z]+)*$
// The nested repetition (+)* causes exponential complexity
const EVIL_REGEX = /^([a-zA-Z]+)*$/;
const rl = readline.createInterface({
input: process.stdin,
output: process.stdout
});
function validateInput(input) {
console.time('Validation Time');
const isValid = EVIL_REGEX.test(input);
console.timeEnd('Validation Time');
if (isValid) {
console.log("Result: Valid Input!");
} else {
console.log("Result: Invalid Input!");
}
}
console.log("--- ReDoS Learning Lab ---");
rl.question('Enter a string to validate: ', (userInput) => {
validateInput(userInput);
rl.close();
});