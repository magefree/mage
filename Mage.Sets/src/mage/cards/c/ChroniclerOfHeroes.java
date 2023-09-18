package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ChroniclerOfHeroes extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_A_CREATURE_P1P1);

    public ChroniclerOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Chronicler of Heroes enters the battlefield, draw a card if you control a creature with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "draw a card if you control a creature with a +1/+1 counter on it")));
    }

    private ChroniclerOfHeroes(final ChroniclerOfHeroes card) {
        super(card);
    }

    @Override
    public ChroniclerOfHeroes copy() {
        return new ChroniclerOfHeroes(this);
    }
}
