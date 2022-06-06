package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CastleVantress extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ISLAND);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public CastleVantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Castle Vantress enters the battlefield tapped unless you control an Island.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new TapSourceEffect(), condition
        ), "tapped unless you control an Island"));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{U}{U}, {T}: Scry 2.
        Ability ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleVantress(final CastleVantress card) {
        super(card);
    }

    @Override
    public CastleVantress copy() {
        return new CastleVantress(this);
    }
}
