package animation;

public class Camera {

	// Camera Position
	private static float x;
	private static float y;

	private static int dir;

	// Camera Acceleration
	private float maxVelY = (float) 32.75;
	private float maxVelX = (float) 41.5;
	private float minVel = 1;

	private float baseX;
	private float baseY;

	private float velX;
	private float velY;

	// Check vars
	private static boolean camMove;

	private boolean countDown;

	// Constructor
	public Camera(float x, float y) {

		this.x = x;
		this.y = y;
		baseX = x;
		baseY = y;

	}

	public void update() {

		// Check if the camera is supposed to move
		if (camMove) {

			// Checks the direction the camera is supposed to move in
			if (dir == 1) {

				// Moves the camera down, and checks if the countdown reaches its max
				if (velY < maxVelY && !countDown) {

					// Increases velocity
					velY += 1.5;

					// Caps velocity
					if (velY > maxVelY) {

						velY = maxVelY;

					}

					// Checks if the velocity reached max
				} else if (velY >= maxVelY || countDown) {

					countDown = true;

					// Decrease vel
					velY -= 1.75;

					// Cap velocity
					if (velY < minVel) {

						velY = minVel;

					}

				}

				// Adds vel
				y += velY;

				// Stops movement once it moves to the next room
				if (y >= baseY + 704 - 64) {

					// Caps y val
					y = baseY + 704 - 64;

					// Stops movement
					camMove = false;

					// Reset vars
					dir = 0;
					velY = 0;
					countDown = false;
					baseY = y;

				}

			}

			// Move Up
			if (dir == 2) {

				if (velY < maxVelY && !countDown) {

					velY += 1.25;

					if (velY > maxVelY) {

						velY = maxVelY;

					}

				} else if (velY >= maxVelY || countDown) {

					countDown = true;

					velY -= 2;

					if (velY < minVel) {

						velY = minVel;

					}

				}

				y -= velY;

				if (y <= baseY - 704 + 64) {

					y = baseY - 704 + 64;
					camMove = false;
					dir = 0;
					velY = 0;
					countDown = false;
					baseY = y;

				}

			}

			// Move right
			if (dir == 3) {

				if (velX < maxVelX && !countDown) {

					velX += 1.5;

					if (velX > maxVelX) {

						velX = maxVelX;

					}

				} else if (velX >= maxVelX || countDown) {

					countDown = true;

					velX -= 2;

					if (velX < minVel) {

						velX = minVel;

					}

				}

				x += velX;

				if (x >= baseX + 1088 - 64) {

					x = baseX + 1088 - 64;
					camMove = false;
					dir = 0;
					velX = 0;
					countDown = false;
					baseX = x;

				}

				// Move Left
			} else if (dir == 4) {

				if (velX < maxVelX && !countDown) {

					velX += 1.5;

					if (velX > maxVelX) {

						velX = maxVelX;

					}

				} else if (velX >= maxVelX || countDown) {

					countDown = true;

					if (velX < minVel) {

						velX = minVel;

					}

				}

				x -= velX;

				if (x <= baseX - 1088 + 64) {

					x = baseX - 1088 + 64;
					camMove = false;
					dir = 0;
					velX = 0;
					countDown = false;
					baseX = x;

				}

			}

		}

	}

	// Sets the direction of the camera and starts moving it
	public static void setMove(int direction) {

		dir = direction;

		camMove = true;

	}

	// Getters and setters
	public static float getX() {

		return x;

	}

	public static boolean getCamMove() {

		return camMove;

	}

	public void setX(float x) {

		this.x = x;

	}

	public static float getY() {

		return y;

	}

	public void setY(float y) {

		this.y = y;

	}

}
