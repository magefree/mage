package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlimmerBairn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public GlimmerBairn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice a token: Glimmer Bairn gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ));
    }

    private GlimmerBairn(final GlimmerBairn card) {
        super(card);
    }

    @Override
    public GlimmerBairn copy() {
        return new GlimmerBairn(this);
    }
}
