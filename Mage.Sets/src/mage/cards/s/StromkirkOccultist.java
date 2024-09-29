
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class StromkirkOccultist extends CardImpl {

    public StromkirkOccultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Stromkirk Mystic deals combat damage to a player, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                .withTextOptions("that card", false), false));

        // Madness {1}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private StromkirkOccultist(final StromkirkOccultist card) {
        super(card);
    }

    @Override
    public StromkirkOccultist copy() {
        return new StromkirkOccultist(this);
    }
}
