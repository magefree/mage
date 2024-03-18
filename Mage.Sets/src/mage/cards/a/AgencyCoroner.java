package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class AgencyCoroner extends CardImpl {

    public AgencyCoroner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // {2}{B}, Sacrifice another creature: Draw a card. If the sacrificed creature was suspected, draw two cards instead.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(2), new DrawCardSourceControllerEffect(1),
                AgencyCoronerCondition.instance,
                "draw a card. If the sacrificed creature was suspected, draw two cards instead"
        ), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);

    }

    private AgencyCoroner(final AgencyCoroner card) {
        super(card);
    }

    @Override
    public AgencyCoroner copy() {
        return new AgencyCoroner(this);
    }
}

enum AgencyCoronerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .anyMatch(Permanent::isSuspected);
    }

    @Override
    public String toString() {
        return "the sacrificed creature was suspected";
    }
}
