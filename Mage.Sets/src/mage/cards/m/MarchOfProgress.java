package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MarchOfProgress extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public MarchOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it.
        // Overload {6}{U}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{6}{U}"),
                new TargetPermanent(filter), new CreateTokenCopyTargetEffect()
                        .setText("Choose target artifact creature you control. For each creature chosen this way, create a token that's a copy of it."));
    }

    private MarchOfProgress(final MarchOfProgress card) {
        super(card);
    }

    @Override
    public MarchOfProgress copy() {
        return new MarchOfProgress(this);
    }
}
