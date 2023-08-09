package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AetherCharge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Beast you control");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }

    public AetherCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");

        // Whenever a Beast enters the battlefield under your control, you may have it deal 4 damage to target opponent.
        Ability ability = new AetherChargeTriggeredAbility();
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private AetherCharge(final AetherCharge card) {
        super(card);
    }

    @Override
    public AetherCharge copy() {
        return new AetherCharge(this);
    }
}

class AetherChargeTriggeredAbility extends TriggeredAbilityImpl {

    public AetherChargeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AetherChargeEffect(), true); // is optional
    }

    public AetherChargeTriggeredAbility(AetherChargeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game) && permanent.hasSubtype(SubType.BEAST, game)
                && permanent.isControlledBy(this.controllerId)) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Beast enters the battlefield under your control, you may have it deal 4 damage to target opponent or planeswalker.";
    }

    @Override
    public AetherChargeTriggeredAbility copy() {
        return new AetherChargeTriggeredAbility(this);
    }
}

class AetherChargeEffect extends OneShotEffect {

    public AetherChargeEffect() {
        super(Outcome.Damage);
        staticText = "you may have it deal 4 damage to target opponent or planeswalker";
    }

    public AetherChargeEffect(final AetherChargeEffect effect) {
        super(effect);
    }

    @Override
    public AetherChargeEffect copy() {
        return new AetherChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD);
        }
        if (creature != null) {
            return game.damagePlayerOrPermanent(source.getFirstTarget(), 4, creature.getId(), source, game, false, true) > 0;
        }
        return false;
    }
}
