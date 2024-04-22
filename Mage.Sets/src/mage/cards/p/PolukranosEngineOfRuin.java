package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class PolukranosEngineOfRuin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.HYDRA, "nontoken Hydra you control");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public PolukranosEngineOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setWhite(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Polukranos, Engine of Ruin or another nontoken Hydra you control dies, create a 3/3 green and white Phyrexian Hydra creature token with reach and a 3/3 green and white Phyrexian Hydra creature token with lifelink.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new PhyrexianHydraWithReachToken()), false, filter
        );
        ability.addEffect(new CreateTokenEffect(new PhyrexianHydraWithLifelinkToken())
                .setText("and a 3/3 green and white Phyrexian Hydra creature token with lifelink"));
        this.addAbility(ability);
    }

    private PolukranosEngineOfRuin(final PolukranosEngineOfRuin card) {
        super(card);
    }

    @Override
    public PolukranosEngineOfRuin copy() {
        return new PolukranosEngineOfRuin(this);
    }
}
