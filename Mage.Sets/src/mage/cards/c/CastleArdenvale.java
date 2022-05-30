package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastleArdenvale extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PLAINS);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public CastleArdenvale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Castle Ardenvale enters the battlefield tapped unless you control a Plains.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new TapSourceEffect(), condition
        ), "tapped unless you control a Plains"));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {2}{W}{W}, {T}: Create a 1/1 white Human creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new HumanToken()), new ManaCostsImpl<>("{2}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleArdenvale(final CastleArdenvale card) {
        super(card);
    }

    @Override
    public CastleArdenvale copy() {
        return new CastleArdenvale(this);
    }
}
