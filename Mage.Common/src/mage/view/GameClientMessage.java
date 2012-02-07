/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.view;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameClientMessage implements Serializable {
    private static final long serialVersionUID = 1L;

	private GameView gameView;
	private CardsView cardsView;
	private CardsView cardsView2;
	private String message;
	private AbilityPickerView abilityView;
	private boolean flag;
	private String[] strings;
	private Set<UUID> targets;
	private int min;
	private int max;
	private Map<String, Serializable> options;

	public GameClientMessage(GameView gameView) {
		this.gameView = gameView;
	}

	public GameClientMessage(GameView gameView, String message) {
		this.gameView = gameView;
		this.message = message;
	}

	private GameClientMessage(GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required) {
		this.gameView = gameView;
		this.message = question;
		this.cardsView = cardView;
		this.targets = targets;
		this.flag = required;
	}

	public GameClientMessage(GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
		this(gameView, question, cardView, targets, required);
		this.options = options;
	}

	public GameClientMessage(String[] choices, String message) {
		this.strings = choices;
		this.message = message;
	}

	public GameClientMessage(String message, int min, int max) {
		this.message = message;
		this.min = min;
		this.max = max;
	}
	
	public GameClientMessage(String message, CardsView pile1, CardsView pile2) {
		this.message = message;
		this.cardsView = pile1;
		this.cardsView2 = pile2;
	}

	public GameClientMessage(CardsView cardView, String name) {
		this.cardsView = cardView;
		this.message = name;
	}

	public GameView getGameView() {
		return gameView;
	}

	public CardsView getCardsView() {
		return cardsView;
	}

	public String getMessage() {
		return message;
	}

	public AbilityPickerView getAbilityView() {
		return abilityView;
	}

	public boolean isFlag() {
		return flag;
	}

	public String[] getStrings() {
		return strings;
	}

	public Set<UUID> getTargets() {
		return targets;
	}

	public CardsView getPile1() {
		return cardsView;
	}
	
	public CardsView getPile2() {
		return cardsView2;
	}
	
	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public Map<String, Serializable> getOptions() {
		return options;
	}
}
