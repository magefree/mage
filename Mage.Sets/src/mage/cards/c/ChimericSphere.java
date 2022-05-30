
package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LoneFox
 */
public final class ChimericSphere extends CardImpl {

    public ChimericSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Until end of turn, Chimeric Sphere becomes a 2/1 Construct artifact creature with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(2, 1, "2/1 Construct artifact creature with flying")
                        .withSubType(SubType.CONSTRUCT)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                "", Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));

        // {2}: Until end of turn, Chimeric Sphere becomes a 3/2 Construct artifact creature without flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 2, "3/2 Construct artifact creature without flying")
                        .withSubType(SubType.CONSTRUCT)
                        .withType(CardType.ARTIFACT),
                "", Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private ChimericSphere(final ChimericSphere card) {
        super(card);
    }

    @Override
    public ChimericSphere copy() {
        return new ChimericSphere(this);
    }
}

