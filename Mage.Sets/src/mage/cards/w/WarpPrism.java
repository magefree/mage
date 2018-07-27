package mage.cards.w;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author NinthWorld
 */
public final class WarpPrism extends CardImpl {

    public WarpPrism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // {T}: Add {W} or {U} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost()));

        // {2}{W}{U}: Warp Prism becomes a 3/3 white and blue Protoss artifact creature with flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(
                        new CreatureToken(3, 3, "3/3 white and blue Protoss artifact creature with flying", SubType.PROTOSS)
                                .withType(CardType.ARTIFACT)
                                .withColor("W").withColor("U")
                                .withAbility(FlyingAbility.getInstance()),
                        "", Duration.EndOfTurn, true, false),
                new ManaCostsImpl("{2}{W}{U}")));
    }

    public WarpPrism(final WarpPrism card) {
        super(card);
    }

    @Override
    public WarpPrism copy() {
        return new WarpPrism(this);
    }
}
