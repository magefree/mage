
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SquirrelToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class FormOfTheSquirrel extends CardImpl {

    public FormOfTheSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // As Form of the Squirrel enters the battlefield, create a 1/1 green Squirrel creature token. You lose the game when that creature leaves the battlefield.
        this.addAbility(new AsEntersBattlefieldAbility(new FormOfTheSquirrelCreateTokenEffect()));

        // Creatures can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouAllEffect(Duration.WhileOnBattlefield)));

        // You have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(ShroudAbility.getInstance())));

        // You can't cast spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FormOfTheSquirrelCantCastEffect()));
    }

    private FormOfTheSquirrel(final FormOfTheSquirrel card) {
        super(card);
    }

    @Override
    public FormOfTheSquirrel copy() {
        return new FormOfTheSquirrel(this);
    }
}

class FormOfTheSquirrelCreateTokenEffect extends OneShotEffect {

    public FormOfTheSquirrelCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 green Squirrel creature token. You lose the game when that creature leaves the battlefield";
    }

    private FormOfTheSquirrelCreateTokenEffect(final FormOfTheSquirrelCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        if (sourceController != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new SquirrelToken());
            effect.apply(game, source);
            game.getState().setValue(source.getSourceId() + "_token", effect.getLastAddedTokenIds());
            for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
                Effect loseGameEffect = new LoseGameTargetPlayerEffect();
                loseGameEffect.setTargetPointer(new FixedTarget(sourceController.getId(), game));
                LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(loseGameEffect, false);
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(triggerAbility, Duration.WhileOnBattlefield);
                continuousEffect.setTargetPointer(new FixedTarget(addedTokenId, game));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public FormOfTheSquirrelCreateTokenEffect copy() {
        return new FormOfTheSquirrelCreateTokenEffect(this);
    }
}

class FormOfTheSquirrelCantCastEffect extends ContinuousRuleModifyingEffectImpl {
    
    public FormOfTheSquirrelCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast spells";
    }

    private FormOfTheSquirrelCantCastEffect(final FormOfTheSquirrelCantCastEffect effect) {
        super(effect);
    }

    @Override
    public FormOfTheSquirrelCantCastEffect copy() {
        return new FormOfTheSquirrelCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
