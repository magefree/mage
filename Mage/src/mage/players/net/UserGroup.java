package mage.players.net;

/**
 * @author ayrat
 */
public enum UserGroup {

	COMPUTER(0),
	PLAYER(1),
	MAGE(3),
	ADMIN(7);

	private int groupId;

	UserGroup(int groupId) {
		this.groupId = groupId;
	}

	public int getGroupId() {
		return this.groupId;
	}
}
