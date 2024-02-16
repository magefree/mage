package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AmaliaBenavidesAguirre extends CardImpl {

    private final static FilterPermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AmaliaBenavidesAguirre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward--Pay 3 life.
        this.addAbility(new WardAbility(new PayLifeCost(3), false));

        // Whenever you gain life, Amalia Benavides Aguirre explores. Then, destroy all other creatures if Amalia's power is exactly 20.
        Ability ability = new GainLifeControllerTriggeredAbility(new ExploreSourceEffect(false, "{this}"));
        ability.addEffect(new ConditionalOneShotEffect(
                new DestroyAllEffect(filter),
                AmaliaBenavidesAguirreCondition.instance
        ).setText("Then, destroy all other creatures if its power is exactly 20"));
        this.addAbility(ability);
    }

    private AmaliaBenavidesAguirre(final AmaliaBenavidesAguirre card) {
        super(card);
    }

    @Override
    public AmaliaBenavidesAguirre copy() {
        return new AmaliaBenavidesAguirre(this);
    }
}

enum AmaliaBenavidesAguirreCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent amalia = source.getSourcePermanentOrLKI(game);
        return amalia != null && amalia.getPower().getValue() == 20;
    }
}
