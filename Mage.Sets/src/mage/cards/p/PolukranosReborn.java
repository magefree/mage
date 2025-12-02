package mage.cards.p;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PhyrexianHydraWithLifelinkToken;
import mage.game.permanent.token.PhyrexianHydraWithReachToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PolukranosReborn extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.HYDRA, "nontoken Hydra you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public PolukranosReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HYDRA}, "{G}{G}{G}",
                "Polukranos, Engine of Ruin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.HYDRA}, "GW");

        // Polukranos Reborn
        this.getLeftHalfCard().setPT(4, 5);

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // {6}{W/P}: Transform Polukranos Reborn. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{6}{W/P}")));

        // Polukranos, Engine of Ruin
        this.getRightHalfCard().setPT(6, 6);

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever Polukranos, Engine of Ruin or another nontoken Hydra you control dies, create a 3/3 green and white Phyrexian Hydra creature token with reach and a 3/3 green and white Phyrexian Hydra creature token with lifelink.
        this.getRightHalfCard().addAbility(new DiesThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new PhyrexianHydraWithReachToken()).withAdditionalTokens(new PhyrexianHydraWithLifelinkToken()), false, filter
        ));
    }

    private PolukranosReborn(final PolukranosReborn card) {
        super(card);
    }

    @Override
    public PolukranosReborn copy() {
        return new PolukranosReborn(this);
    }
}
