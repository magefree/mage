/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;

/**
 * A simple {@link Condition} to invert a decorated conditions
 * {@link Condition#apply(mage.game.Game, mage.abilities.Ability) apply(mage.game.Game, mage.abilities.Ability)}
 *  method invocation.
 * 
 * @author maurer.it_at_gmail.com
 */
public class Unless implements Condition {

	private Condition condition;

	public Unless ( Condition condition ) {
		this.condition = condition;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean apply(Game game, Ability source) {
		return !condition.apply(game, source);
	}

}
