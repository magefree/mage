
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class RumblingAftershocks extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public RumblingAftershocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");


        // Whenever you cast a kicked spell, you may have Rumbling Aftershocks deal damage to any target equal to the number of times that spell was kicked.
        Ability ability = new RumblingAftershocksTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    public RumblingAftershocks(final RumblingAftershocks card) {
        super(card);
    }

    @Override
    public RumblingAftershocks copy() {
        return new RumblingAftershocks(this);
    }
}

class RumblingAftershocksTriggeredAbility extends TriggeredAbilityImpl {

    RumblingAftershocksTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RumblingAftershocksDealDamageEffect(), true);
    }

    RumblingAftershocksTriggeredAbility(final RumblingAftershocksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RumblingAftershocksTriggeredAbility copy() {
        return new RumblingAftershocksTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isControlledBy(controllerId)) {
            int damageAmount = 0;
            for (Ability ability: spell.getAbilities()) {
                if (ability instanceof KickerAbility) {
                    damageAmount += ((KickerAbility) ability).getKickedCounter(game, spell.getSpellAbility());
                }
            }
            if (damageAmount > 0) {
                this.getEffects().get(0).setValue("damageAmount", damageAmount);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a kicked spell, " + super.getRule();
    }
}

class RumblingAftershocksDealDamageEffect extends OneShotEffect {

    public RumblingAftershocksDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have {this} deal damage to any target equal to the number of times that spell was kicked";
    }

    public RumblingAftershocksDealDamageEffect(final RumblingAftershocksDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public RumblingAftershocksDealDamageEffect copy() {
        return new RumblingAftershocksDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        if (player != null && damageAmount > 0) {
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
               targetPlayer.damage(damageAmount, source.getSourceId(), game, false, true);
               return true;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(damageAmount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
