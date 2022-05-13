
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class IreShaman extends CardImpl {

    public IreShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());

        // Megamorph {R}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{R}"), true));

        // When Ire Shaman is turned face up, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), false));
    }

    private IreShaman(final IreShaman card) {
        super(card);
    }

    @Override
    public IreShaman copy() {
        return new IreShaman(this);
    }
}
