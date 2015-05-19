package mexica.multiplayer.core;

import mexica.core.utilities.DataBuilder;
import mexica.multiplayer.core.CommStandards.CommChat;
import mexica.multiplayer.core.CommStandards.CommConnection;
import mexica.multiplayer.core.CommStandards.CommGameLobby;
import mexica.multiplayer.core.CommStandards.CommOnlineGame;
import mexica.multiplayer.core.CommStandards.CommServer;

public final class PacketType {

	public static final int UNDEFINED = 00;

	/** Packets a Server sends. */
	public static class Server {
		/** Packets about the connection with the client. */
		public static class Connection {
			/** Packets about authentication of the client. */
			public static class Authentication {
				/**
				 * Packet containing new token for the client. "to" specifies a
				 * token.
				 */
				public static final int Token = -10;

				public static final Packet generateTokenPacket(String token) {
					return new Packet(Token, new DataBuilder()
							.add(CommConnection.Token).wrap(token).getString());
				}

				/** Packets about the user login of a client. */
				public static class Login {
					/**
					 * Packet containing response for authentication request of
					 * the client. "a" specifies either "true" or "false".
					 */
					// public static final int Response = -20;

					public static final int Response = -1
							* PacketType.Client.Connection.Authentication.Login.Request;

					public static final Packet generateResponsePacket(
							boolean answer) {
						return new Packet(Response, new DataBuilder()
								.add(CommConnection.Answer)
								.wrap(CommStandards.parseBoolean(answer))
								.getString());
					}
				}

				/** Packets about registering the client as a new user. */
				public static class Register {
					/**
					 * Packet containing response for register request of the
					 * client. "a" specifies either "t" or "f".
					 */
					// public static final int Response = -60;
					public static final int Response = -1
							* PacketType.Client.Connection.Authentication.Register.Request;

					public static final Packet generateResponsePacket(
							boolean answer) {
						return new Packet(Response, new DataBuilder()
								.add(CommConnection.Answer)
								.wrap(CommStandards.parseBoolean(answer))
								.getString());
					}
				}
			}

			/**
			 * Packet telling the client it has been kicked from the server.
			 * Does not actually kick the client. "r" specifies the reason.
			 */
			public static final int Kick = -30;

			public static final Packet generateKickPacket(String reason) {
				return new Packet(Kick, new DataBuilder()
						.add(CommConnection.Reason).wrap(reason).getString());
			}

			/**
			 * Packet telling other clients a new client has joined. "u"
			 * specifies the username of the client.
			 */
			public static final int Join = -40;

			public static final Packet generateJoinPacket(String username) {
				return new Packet(Join, new DataBuilder()
						.add(CommConnection.Username).wrap(username)
						.getString());
			}

			/**
			 * Packet telling other clients a client has left the server. "u"
			 * specifies the username of the client.
			 */
			public static final int Leave = -50;

			public static final Packet generateLeavePacket(String username) {
				return new Packet(Leave, new DataBuilder()
						.add(CommConnection.Username).wrap(username)
						.getString());
			}

			/**
			 * Friendly version of Kick. Packet telling the client the reason
			 * for a disconnect from the server. "r" specifies the reason.
			 */
			public static final int Disconnect = -70;

			public static final Packet generateDisconnectPacket(String reason) {
				return new Packet(Disconnect, new DataBuilder()
						.add(CommConnection.Reason).wrap(reason).getString());
			}
		}

		/** Packets about chat messages from the server */
		public static class Chat {
			/**
			 * General packet used for chatting on the server. "s" is the
			 * username of the sender of the message. "m" is the message.
			 */
			public static final int General = 140;

			public static final Packet generateGeneralChatPacket(
					String sendersname, String message) {
				return new Packet(General, new DataBuilder()
						.add(CommChat.Sender).wrap(sendersname)
						.add(CommChat.ChatMessage).wrap(message).getString());
			}

			/**
			 * Packet containing a chat message for all clients inside a game.
			 * data[0] must contain chat message.
			 */
			@Deprecated
			public static final int Game = -110;
			/**
			 * Packet containing a chat message for all clients on a server.
			 * data[0] must contain a chat message.
			 */
			@Deprecated
			public static final int Server = -120;
			/**
			 * Packet containing a personal message for a client. data[0]
			 * specifies the message.
			 */
			@Deprecated
			public static final int Personal = -130;
		}

		/**
		 * Packets about the state of a Game. Not about GamePlay!
		 * 
		 * @see Play
		 */
		public static class GameLobby {
			public static class Join {
				/**
				 * Packet containing a response to a Join Request of a client.
				 * "a" must contain either "true" or "false".
				 */
				// public static final int Response = -210;
				public static final int Response = -1
						* PacketType.Client.GameLobby.Join.Request;

				public static final Packet generateResponsePacket(boolean answer) {
					return new Packet(Response, new DataBuilder()
							.add(CommConnection.Answer)
							.wrap(CommStandards.parseBoolean(answer))
							.getString());
				}
			}

			/**
			 * Packet containing the new game settings for all (potential)
			 * players of a lobby. "ls" specifies the new lobbysettings.
			 */
			public static final int LobbySettings = -220;

			public static final Packet generateLobbySettingsPacket(
					String lobbysettings) {
				return new Packet(LobbySettings, new DataBuilder()
						.add(CommGameLobby.GameLobbySettings)
						.wrap(lobbysettings).getString());
			}

			/**
			 * Packet telling a client it has been kicked from the lobby. "r"
			 * specifies the reason.
			 */
			public static final int Kick = -230;

			public static final Packet generateKickPacket(String reason) {
				return new Packet(Kick, new DataBuilder()
						.add(CommConnection.Reason).wrap(reason).getString());
			}

			/**
			 * Packet telling other players of a game the status of the game
			 * changed. "s" specifies the status of the onlinegame.
			 */
			public static final int Status = -240;

			public static final Packet generateGameStatusPacket(String status) {
				return new Packet(Status, new DataBuilder()
						.add(CommGameLobby.Status).wrap(status).getString());
			}

			/**
			 * Packet telling other clients a new lobby has been created. "n"
			 * specifies the lobbys name. "a" specifies the username of the
			 * lobby host. "ls" specifies the lobbysettings.
			 */
			public static final int Create = -250;

			public static final Packet generateLobbyCreatePacket(
					String gamesname, String admin, String lobbysettings) {
				return new Packet(Create, new DataBuilder()
						.add(CommGameLobby.GameLobbyName).wrap(gamesname)
						.add(CommGameLobby.Admin).wrap(admin)
						.add(CommGameLobby.GameLobbySettings)
						.wrap(lobbysettings).getString());
			}

			/**
			 * Packet telling other clients a lobby has been removed. "n"
			 * specifies the lobbys name. "a" specifies the user of the lobby
			 * host.
			 */
			public static final int Remove = -260;

			public static final Packet generateLobbyRemovePacket(
					String gamesname, String admin) {
				return new Packet(Remove, new DataBuilder()
						.add(CommGameLobby.GameLobbyName).wrap(gamesname)
						.add(CommGameLobby.Admin).wrap(admin).getString());
			}

			/** Packet telling other players the status of the client. */
			@Deprecated
			public static final int ClientStatus = -270;

		}

		/** Packets concerning the gameplay. */
		public static class Play {
			/** Packets concerning actions in the game. */
			public static class GameAction {
				/** Packets concerning the verification of a players action. */
				public static class Verification {
					/**
					 * Packet containing a response to a verification request of
					 * a client. "a" must specify the playeraction. data[1] must
					 * specify the answer: "true" or "false".
					 */
					// public static final int Response = -310;
					public static final int Response = -1
							* PacketType.Client.Play.GameAction.Verification.Request;

					public static final Packet generateResponsePacket(
							boolean answer) {
						return new Packet(Response, new DataBuilder()
								.add(CommConnection.Answer)
								.wrap(CommStandards.parseBoolean(answer))
								.getString());
					}

					/**
					 * Packet requesting the verification of a players action.
					 * "ga" must specify the gameaction.
					 */
					public static final int Request = -320;

					public static final Packet generateRequestPacket(
							String gameaction) {
						return new Packet(
								Request,
								new DataBuilder()
										.add(CommOnlineGame.CommOnlineGameAction.OnlineGameAction)
										.wrap(gameaction).getString());
					}
				}

				/**
				 * Packet telling players an action has been done. "ga" must
				 * specify the gameaction.
				 */
				public static final int Do = -330;

				public static final Packet generateDoPacket(String gameaction) {
					return new Packet(
							Do,
							new DataBuilder()
									.add(CommOnlineGame.CommOnlineGameAction.OnlineGameAction)
									.wrap(gameaction).getString());
				}

				/**
				 * Packet telling players an action has been undone. "ga" must
				 * specify the gameaction.
				 */
				public static final int Undo = -340;

				public static final Packet generateUndoPacket(String gameaction) {
					return new Packet(
							Undo,
							new DataBuilder()
									.add(CommOnlineGame.CommOnlineGameAction.OnlineGameAction)
									.wrap(gameaction).getString());
				}
			}
		}

		/** Packets sending server info. */
		public static class Info {
			/**
			 * Packet sending the version of the server. "sv" must specify the
			 * version.
			 */
			public static final int ServerVersion = -410;

			public static final Packet generateServerVersionPacket() {
				return new Packet(ServerVersion, new DataBuilder()
						.add(CommServer.ServerVersion)
						.wrap(mexica.multiplayer.server.Server.VERSION)
						.getString());
			}

			/**
			 * Packet sending a list of all online players on the server. "ps"
			 * specifies the list.
			 */
			public static final int OnlinePlayers = -420;

			public static final Packet generateOnlinePlayersPacket(
					String onlineplayers) {
				return new Packet(OnlinePlayers, new DataBuilder()
						.add(CommServer.OnlinePlayers).wrap(onlineplayers)
						.getString());
			}

			/**
			 * Packet sending a list of all running games, including the
			 * username of the host. "l" specifies the list.
			 */
			public static final int Lobbys = -430;

			public static final Packet generateLobbysInfoPacket(
					String lobbysinfo) {
				return new Packet(Lobbys, new DataBuilder()
						.add(CommServer.Lobbys).wrap(lobbysinfo).getString());
			}
		}
	}

	/** Packets a client sends. */
	public static class Client {
		/**
		 * Packets concerning the connection with the server. The type (int)
		 * ranges from 1 till 11.
		 */
		public static class Connection {
			/** Packets concerning authentication with the server. */
			public static class Authentication {
				/** Packets concerning the login protocol */
				public static class Login {
					/**
					 * Packet requesting to log in on the server. data[0] is the
					 * username data[1] is the password, hashed with the token
					 * received from the server.
					 */
					public static final int Request = 20;
				}

				/** Packets concerning a new user. */
				public static class Register {
					/**
					 * Packet requesting a new User account on the server.
					 * data[0] is the username data[1] is the password, hashed
					 * with the token received from the server.
					 */
					public static final int Request = 60;
				}
			}

			/** Packet telling the server ??? */
			@Deprecated
			public static final int Disconnect = 70;
		}

		/**
		 * Packets concerning chatting. The type (int) ranges from 11 till 21.
		 */
		public static class Chat {
			/**
			 * General packet to chat on the server. data[0] must specify the
			 * username the message is meant for. data[1] is the message.
			 */
			public static final int General = 140;
			@Deprecated
			public static final int Game = 110;
			@Deprecated
			public static final int Server = 120;
			@Deprecated
			public static final int Personal = 130;
		}

		/**
		 * Packets concerning games on the server The type (int) ranges from 21
		 * till 31.
		 */
		public static class GameLobby {
			/** Packets concerning joining a game. */
			public static class Join {
				/**
				 * Packet requesting to join the game. data[0] is the games
				 * name.
				 */
				public static final int Request = 210;

				public static final Packet generateRequestPacket(
						String gamesname) {
					return new Packet(Request, new DataBuilder()
							.add(CommOnlineGame.Name).wrap(gamesname)
							.getString());
				}

			}

			/**
			 * Packet updating the settings of a game. data[0] is the new game
			 * settings.
			 */
			public static final int LobbySettings = 220;
			/**
			 * Packet kicking a player from a game. data[0] is the players name.
			 * data[1] is the reason.
			 */
			public static final int Kick = 230;

			/**
			 * Packet updating the status of a game. data[0] is either "B", "F"
			 * or "S"
			 */
			public static final int Status = 240;
			/** Packet telling the server it will leave a game. */
			public static final int Leave = 280;
			/**
			 * Packet creating a new game on the server. Data contains the lobby
			 * settings.
			 */
			public static final int Create = 250;
			/** Packet removing a game from the server. */
			public static final int Remove = 260;
			/** Packet ??? */
			public static final int ClientStatus = 270;
		}

		/**
		 * Packets concerning gameplay. The type (int) ranges from 31 till 41.
		 */
		public static class Play {
			/** Packets concerning game actions. */
			public static class GameAction {
				/** Packets concerning the verification of game actions; */
				public static class Verification {
					/**
					 * Packet giving a response to a verification from the
					 * server. data[0] is the players action. data[1] is the
					 * answer, either "T" or "F".
					 */
					// public static final int Response = 310;
					public static final int Response = -1
							* PacketType.Server.Play.GameAction.Verification.Request;
					/**
					 * Packet request the validation of a players action.
					 * data[0] is the players action.
					 */
					public static final int Request = 320;
				}

				/**
				 * Packet requesting to do a clients action in a game. data[0]
				 * is the playeraction.
				 */
				public static final int Do = 330;
				/**
				 * Packet requesting to undo a clients action in a game. data[0]
				 * is the playeraction.
				 */
				public static final int Undo = 340;
			}
		}

		/**
		 * Packets concerning the server info. The type (int) ranges from 41
		 * till 51.
		 */
		public static class Info {
			/** Packet requesting the version of the server. */
			public static final int ServerVersion = 410;
			/** Packet requesting a list of online players. */
			public static final int OnlinePlayers = 420;
			/** Packet requesting a list of running games. */
			public static final int Lobbys = 430;
		}
	}

	public static boolean isConnectionPacket(int packettype) {
		packettype = Math.abs(packettype);
		return (packettype > 00 && packettype < 110);
	}

	public static boolean isChatPacket(int packettype) {
		packettype = Math.abs(packettype);
		return (packettype > 100 && packettype < 210);
	}

	public static boolean isGamePacket(int packettype) {
		packettype = Math.abs(packettype);
		return (packettype > 200 && packettype < 310);
	}

	public static boolean isPlayPacket(int packettype) {
		packettype = Math.abs(packettype);
		return (packettype > 300 && packettype < 410);
	}

	public static boolean isInfoPacket(int packettype) {
		packettype = Math.abs(packettype);
		return (packettype > 400 && packettype < 510);
	}
}
