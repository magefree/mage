package mage.cards.v;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;

/**
 * 
 * @author Emigara
 *
 */
public class ValiantRescuer extends CardImpl{

	public ValiantRescuer(UUID ownerId, CardSetInfo setInfo) {
		super(ownerId, setInfo, new CardType[] {CardType.CREATURE}, "{1}{W}");
		
		this.subtype.add(SubType.HUMAN);
		this.subtype.add(SubType.SOLDIER);
		
		this.power = new MageInt(3);
		this.toughness = new MageInt(1);
		
		this.addAbility(new CycleControllerTriggeredAbility(
				new CreateTokenEffect(new HumanSoldierToken())));
		
		this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
	}
	
	public ValiantRescuer(final ValiantRescuer card) {
		super(card);
	}

	@Override
	public Card copy() {
		return new ValiantRescuer(this);
	}

}
