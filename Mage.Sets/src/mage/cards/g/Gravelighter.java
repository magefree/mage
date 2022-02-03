package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.watchers.common.MorbidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gravelighter extends CardImpl {

    public Gravelighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Gravelighter enters the battlefield, draw a card if a creature died this turn. Otherwise, each player sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new SacrificeAllEffect(1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                MorbidCondition.instance, "draw a card if a creature died this turn. " +
                "Otherwise, each player sacrifices a creature"
        )).addHint(MorbidHint.instance), new MorbidWatcher());
    }

    private Gravelighter(final Gravelighter card) {
        super(card);
    }

    @Override
    public Gravelighter copy() {
        return new Gravelighter(this);
    }
}
