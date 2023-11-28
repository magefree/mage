package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class TwistsAndTurns extends CardImpl {

    public TwistsAndTurns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.secondSideCardClazz = mage.cards.m.MycoidMaze.class;

        // If a creature you control would explore, instead you scry 1, then that creature explores.
        this.addAbility(new SimpleStaticAbility(new TwistsAndTurnsReplacementEffect()));

        // When Twists and Turns enters the battlefield, target creature you control explores.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExploreTargetEffect(false));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // When a land enters the battlefield under your control, if you control seven or more lands, transform Twists and Turns.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(new TransformSourceEffect(), StaticFilters.FILTER_LAND),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_LANDS, ComparisonType.MORE_THAN, 6, true),
                "When a land enters the battlefield under your control, if you control seven or more lands, transform {this}."
        ));

    }

    private TwistsAndTurns(final TwistsAndTurns card) {
        super(card);
    }

    @Override
    public TwistsAndTurns copy() {
        return new TwistsAndTurns(this);
    }
}

class TwistsAndTurnsReplacementEffect extends ReplacementEffectImpl {

    TwistsAndTurnsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature you control would explore, instead you scry 1, then that creature explores";
    }

    private TwistsAndTurnsReplacementEffect(final TwistsAndTurnsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TwistsAndTurnsReplacementEffect copy() {
        return new TwistsAndTurnsReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLORE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getControllerId().equals(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        controller.scry(1, source, game);
        return false;
    }
}
