package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CurseOfInertia extends CardImpl {

    public CurseOfInertia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may tap or untap target permanent of their choice.
        this.addAbility(new CurseOfInertiaTriggeredAbility());

    }

    private CurseOfInertia(final CurseOfInertia card) {
        super(card);
    }

    @Override
    public CurseOfInertia copy() {
        return new CurseOfInertia(this);
    }
}

class CurseOfInertiaTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfInertiaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfInertiaTapOrUntapTargetEffect(), false);
    }

    public CurseOfInertiaTriggeredAbility(final CurseOfInertiaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getCombat().getPlayerDefenders(game, false).contains(enchantment.getAttachedTo())) {
            TargetPermanent target = new TargetPermanent();
            target.setTargetController(game.getCombat().getAttackingPlayerId());
            addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks enchanted player with one or more creatures, that attacking player may tap or untap target permanent of their choice.";
    }

    @Override
    public CurseOfInertiaTriggeredAbility copy() {
        return new CurseOfInertiaTriggeredAbility(this);
    }

}

class CurseOfInertiaTapOrUntapTargetEffect extends OneShotEffect {
    public CurseOfInertiaTapOrUntapTargetEffect() {
        super(Outcome.Tap);
        staticText = "tap or untap target permanent of their choice";
    }

    public CurseOfInertiaTapOrUntapTargetEffect(final CurseOfInertiaTapOrUntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getTargetController());
        if (player != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                if (targetPermanent.isTapped()) {
                    if (player.chooseUse(Outcome.Untap, "Untap that permanent?", source, game)) {
                        targetPermanent.untap(game);
                    }
                } else {
                    if (player.chooseUse(Outcome.Tap, "Tap that permanent?", source, game)) {
                        targetPermanent.tap(source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public CurseOfInertiaTapOrUntapTargetEffect copy() {
        return new CurseOfInertiaTapOrUntapTargetEffect(this);
    }

}
