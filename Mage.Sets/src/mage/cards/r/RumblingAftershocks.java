package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RumblingAftershocks extends CardImpl {

    public RumblingAftershocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");


        // Whenever you cast a kicked spell, you may have Rumbling Aftershocks deal damage to any target equal to the number of times that spell was kicked.
        Ability ability = new RumblingAftershocksTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private RumblingAftershocks(final RumblingAftershocks card) {
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
        setTriggerPhrase("Whenever you cast a kicked spell, ");
    }

    private RumblingAftershocksTriggeredAbility(final RumblingAftershocksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RumblingAftershocksTriggeredAbility copy() {
        return new RumblingAftershocksTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int kickedCount = KickerAbility.getSpellKickedCount(game, event.getTargetId());
        if (kickedCount > 0) {
            this.getEffects().get(0).setValue("damageAmount", kickedCount);
            return true;
        }
        return false;
    }
}

class RumblingAftershocksDealDamageEffect extends OneShotEffect {

    RumblingAftershocksDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "you may have {this} deal damage to any target equal to the number of times that spell was kicked";
    }

    private RumblingAftershocksDealDamageEffect(final RumblingAftershocksDealDamageEffect effect) {
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
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(damageAmount, source.getSourceId(), source, game);
                return true;
            }
            Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                permanent.damage(damageAmount, source.getSourceId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
