package mage.cards.s;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StuffedBear extends CardImpl {

    public StuffedBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}: Stuffed Bear becomes a 4/4 green Bear artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 green Bear artifact creature until end of turn")
                        .withColor("G")
                        .withSubType(SubType.BEAR)
                        .withType(CardType.ARTIFACT),
                "", Duration.EndOfTurn
        ), new GenericManaCost(2)));
    }

    private StuffedBear(final StuffedBear card) {
        super(card);
    }

    @Override
    public StuffedBear copy() {
        return new StuffedBear(this);
    }
}
