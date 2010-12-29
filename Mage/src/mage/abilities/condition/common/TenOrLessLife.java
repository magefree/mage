/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.condition.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class TenOrLessLife implements Condition {

	public static enum CheckType { AN_OPPONENT, CONTROLLER, TARGET_OPPONENT };

	private CheckType type;

	public TenOrLessLife ( CheckType type ) {
		this.type = type;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		boolean conditionApplies = false;

		switch ( this.type ) {
			case AN_OPPONENT:
				for ( UUID opponentUUID : game.getOpponents(source.getControllerId()) ) {
					conditionApplies |= game.getPlayer(opponentUUID).getLife() <= 10;
				}
				break;
			case CONTROLLER:
				conditionApplies |= game.getPlayer(source.getControllerId()).getLife() <= 10;
				break;
			case TARGET_OPPONENT:
				//TODO: Implement this.
				break;
		}

		return conditionApplies;
	}

}
