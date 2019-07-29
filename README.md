# MarkOverflow
## A discord bot that generates stackoverflow responses using a markov chain

This is a project made for fun. It is made in Kotlin. Feel free to clone, fork or contribute to the project.

### Building

Use this command to build :
`gradle build`

### Running the project

First thing you're going to need is to create a bot on the [discord apps page](https://discordapp.com/developers/applications/). Make sure to be logged in. Then, copy your bot's token.
Next, create a file in your directory called "config.json" following this template :
```json
{
	"prefix" : "char",
	"token" : "string",
	"enableCommandLog" : "boolean"
}
```
Where prefix is your desired command prefix.
To invite your bot to a server, use the OAuth2 tab in your app's page to generate an invite link by selecting "bot" and the required permissions, then paste it in your browser.

Finally, run your jar and the bot should be online!
