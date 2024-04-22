package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ExperimentalConfectioner extends CardImpl {

    public ExperimentalConfectioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Experimental Confectioner enters the battlefield, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you sacrifice a Food, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new CreateTokenEffect(new RatCantBlockToken()), StaticFilters.FILTER_CONTROLLED_FOOD
        ));

    }

    private ExperimentalConfectioner(final ExperimentalConfectioner card) {
        super(card);
    }

    @Override
    public ExperimentalConfectioner copy() {
        return new ExperimentalConfectioner(this);
    }
}
