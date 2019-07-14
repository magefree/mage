
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
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
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.abilities.effects.OneShotEffect;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfThePiercedHeart extends CardImpl {

    public CurseOfThePiercedHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer target = new TargetPlayer();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(target.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, Curse of the Pierced Heart deals 1 damage to that player.
        this.addAbility(new CurseOfThePiercedHeartAbility());
    }

    public CurseOfThePiercedHeart(final CurseOfThePiercedHeart card) {
        super(card);
    }

    @Override
    public CurseOfThePiercedHeart copy() {
        return new CurseOfThePiercedHeart(this);
    }
}

class CurseOfThePiercedHeartAbility extends TriggeredAbilityImpl {

    public CurseOfThePiercedHeartAbility() {
        super(Zone.BATTLEFIELD, new CurseOfThePiercedHeartEffect());
    }

    public CurseOfThePiercedHeartAbility(final CurseOfThePiercedHeartAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfThePiercedHeartAbility copy() {
        return new CurseOfThePiercedHeartAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.sourceId);
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player player = game.getPlayer(enchantment.getAttachedTo());
            if (player != null && game.isActivePlayer(player.getId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of enchanted player's upkeep, "
                + "{this} deals 1 damage to that player or a planeswalker that player controls.";
    }

}

class CurseOfThePiercedHeartEffect extends OneShotEffect {

    public CurseOfThePiercedHeartEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to that player or a planeswalker that player controls";
    }

    public CurseOfThePiercedHeartEffect(final CurseOfThePiercedHeartEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfThePiercedHeartEffect copy() {
        return new CurseOfThePiercedHeartEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (controller == null || enchantment == null) {
            return false;
        }
        UUID opponentId = enchantment.getAttachedTo();
        Player opponent = game.getPlayer(opponentId);
        if (opponent == null) {
            return false;
        }
        if (!game.getBattlefield().getAllActivePermanents(new FilterPlaneswalkerPermanent(), opponentId, game).isEmpty()) {
            if (controller.chooseUse(Outcome.Damage, "Redirect to a planeswalker controlled by " + opponent.getLogName() + "?", source, game)) {
                FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a planeswalker controlled by " + opponent.getLogName());
                filter.add(new ControllerIdPredicate(opponentId));
                TargetPermanent target = new TargetPermanent(1, 1, filter, false);
                if (target.choose(Outcome.Damage, controller.getId(), source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        return permanent.damage(1, source.getSourceId(), game, false, true) > 0;
                    }
                }
            }
        }
        opponent.damage(1, source.getSourceId(), game, false, true);
        return true;
    }
}
