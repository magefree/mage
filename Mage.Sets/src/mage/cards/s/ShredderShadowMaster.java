package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ShredderShadowMaster extends CardImpl {

    public ShredderShadowMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Shredder attacks a player, for each other opponent, create a token that's a copy of Shredder tapped and attacking that player, except it isn't legendary. Sacrifice those tokens at end of combat.
        this.addAbility(new ShredderShadowMasterTriggeredAbility());

        // Whenever Shredder deals combat damage to a player, that player loses half their life, rounded up.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseHalfLifeTargetEffect(), false, true));
    }

    private ShredderShadowMaster(final ShredderShadowMaster card) {
        super(card);
    }

    @Override
    public ShredderShadowMaster copy() {
        return new ShredderShadowMaster(this);
    }
}

class ShredderShadowMasterTriggeredAbility extends TriggeredAbilityImpl {

    ShredderShadowMasterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ShredderShadowMasterEffect());
        setTriggerPhrase("Whenever {this} attacks a player, ");
    }

    private ShredderShadowMasterTriggeredAbility(final ShredderShadowMasterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShredderShadowMasterTriggeredAbility copy() {
        return new ShredderShadowMasterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(game.getCombat().getDefenderId(this.getSourceId()));
        if (player == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(player.getId()));
        return true;
    }
}

class ShredderShadowMasterEffect extends OneShotEffect {

    ShredderShadowMasterEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each other opponent, create a token that's a copy of {this} "
            + "tapped and attacking that player, except it isn't legendary. "
            + "Sacrifice those tokens at the end of combat";
    }

    private ShredderShadowMasterEffect(final ShredderShadowMasterEffect effect) {
        super(effect);
    }

    @Override
    public ShredderShadowMasterEffect copy() {
        return new ShredderShadowMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = source.getSourcePermanentOrLKI(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if (defendingPlayerId == null) {
            return false;
        }

        List<Permanent> tokens = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!playerId.equals(defendingPlayerId) && controller.hasOpponent(playerId, game)) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(controller.getId(), null, false, 1, true, true, playerId).setIsntLegendary(true);
                    effect.setTargetPointer(new FixedTarget(sourceObject, game));
                    effect.apply(game, source);
                    tokens.addAll(effect.getAddedPermanents());
                }
            }
        }
        if (!tokens.isEmpty()) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice those tokens");
            sacrificeEffect.setTargetPointer(new FixedTargets(new ArrayList<>(tokens), game));
            game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(sacrificeEffect), source);
        }
        return true;
    }
}
