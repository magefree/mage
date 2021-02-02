
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class Whiteout extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public Whiteout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // All creatures lose flying until end of turn.
        Effect effect = new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("All creatures lose flying until end of turn");
        this.getSpellAbility().addEffect(effect);

        // Sacrifice a snow land: Return Whiteout from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnToHandSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private Whiteout(final Whiteout card) {
        super(card);
    }

    @Override
    public Whiteout copy() {
        return new Whiteout(this);
    }
}
