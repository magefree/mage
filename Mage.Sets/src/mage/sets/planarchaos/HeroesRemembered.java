package mage.sets.planarchaos;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;

public class HeroesRemembered extends CardImpl{

	public HeroesRemembered(UUID ownerId){
		super(ownerId, 7, "Heroes Remembered", Rarity.RARE, new CardType[] {CardType.SORCERY}, "{6}{W}{W}{W}");
		this.expansionSetCode = "PLC";
		
		//You gain 20 life.
		this.getSpellAbility().addEffect(new GainLifeEffect(20));
		//Suspend 10-{W}
		this.addAbility(new SuspendAbility(10, new ManaCostsImpl("{W}"), this));
	}
	
	public HeroesRemembered(final HeroesRemembered card) {
		super(card);
	}

	@Override
	public Card copy() {
		return new HeroesRemembered(this);
	}

}
