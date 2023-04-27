package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PlayLandCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.PlayLandWatcher;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author awjackson
 */
public final class RockJockey extends CardImpl {

    public RockJockey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You can't cast this spell if you've played a land this turn.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(
                new InvertCondition(PlayLandCondition.instance),
                "You can't cast this spell if you've played a land this turn"
                ), new PlayLandWatcher());

        // You can't play lands if you've cast Rock Jockey this turn.
        this.addAbility(new SimpleStaticAbility(new RockJockeyEffect()), new SpellsCastWatcher());
    }

    private RockJockey(final RockJockey card) {
        super(card);
    }

    @Override
    public RockJockey copy() {
        return new RockJockey(this);
    }
}

class RockJockeyEffect extends ContinuousRuleModifyingEffectImpl {

    public RockJockeyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "You can't play lands if you've cast {this} this turn";
    }

    public RockJockeyEffect(final RockJockeyEffect effect) {
        super(effect);
    }

    @Override
    public RockJockeyEffect copy() {
        return new RockJockeyEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher.getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> spell.getSourceId().equals(source.getSourceId()));
    }
}
