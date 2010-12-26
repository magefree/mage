package mage.client.components;

public enum MageComponents {
	TABLES_MENU_BUTTON("btnGames"),
	NEW_GAME_BUTTON("btnNewTable"),
	NEW_TABLE_OK_BUTTON("btnOK"),
	TABLE_WAITING_START_BUTTON("btnStart"),
	DESKTOP_PANE("desktopPane"),
    CARD_INFO_PANE("cardInfoPane"),
    POPUP_CONTAINER("popupContainer");
	
	private String name;
	MageComponents(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
