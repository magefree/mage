package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SizzlingChangeling extends CardImpl {

    public SizzlingChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When this creature dies, exile the top card of your library. Until the end of your next turn, you may play that card.
        this.addAbility(new DiesSourceTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)));
    }

    private SizzlingChangeling(final SizzlingChangeling card) {
        super(card);
    }

    @Override
    public SizzlingChangeling copy() {
        return new SizzlingChangeling(this);
    }
}
