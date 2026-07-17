package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBlueSpirit extends CardImpl {

    private static final Condition condition = new IsPhaseCondition(TurnPhase.COMBAT);

    public TheBlueSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // You may cast the first creature spell you cast each turn as though it had flash.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CastAsThoughItHadFlashAllEffect(
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_CARD_CREATURE
                ), TheBlueSpiritCondition.instance
        ).setText("you may cast the first creature spell you cast each turn as though it had flash")));

        // Whenever a nontoken creature you control enters during combat, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ).withTriggerCondition(condition));
    }

    private TheBlueSpirit(final TheBlueSpirit card) {
        super(card);
    }

    @Override
    public TheBlueSpirit copy() {
        return new TheBlueSpirit(this);
    }
}

enum TheBlueSpiritCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .noneMatch(spell -> spell.isCreature(game));
    }
}
