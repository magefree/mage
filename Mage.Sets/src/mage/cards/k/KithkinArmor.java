package mage.cards.k;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.events.DamageEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetSource;

/**
 *
 * @author jeffwadsworth
 */
public final class KithkinArmor extends CardImpl {

    public KithkinArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KithkinArmorRestrictionEffect()));

        // Sacrifice Kithkin Armor: The next time a source of your choice would deal damage to enchanted creature this turn, prevent that damage.
        Ability protectionAbility = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new KithkinArmorPreventionEffect(),
                new KithkinArmorCost());
        protectionAbility.addTarget(new TargetSource());
        this.addAbility(protectionAbility);

    }

    private KithkinArmor(final KithkinArmor card) {
        super(card);
    }

    @Override
    public KithkinArmor copy() {
        return new KithkinArmor(this);
    }
}

class KithkinArmorCost extends CostImpl {

    public KithkinArmorCost() {
        this.text = "sacrifice {this}";
    }

    public KithkinArmorCost(KithkinArmorCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            // store attached to information due to getLastInfo being completely fubared
            game.getState().setValue(ability.getSourceId().toString() + "attachedToPermanent", permanent.getAttachedTo());
            paid = permanent.sacrifice(source, game);
            if (!paid) {
                game.getState().setValue(ability.getSourceId().toString() + "attachedToPermanent", null);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && game.getPlayer(controllerId).canPaySacrificeCost(permanent, source, controllerId, game);
    }

    @Override
    public KithkinArmorCost copy() {
        return new KithkinArmorCost(this);
    }
}

class KithkinArmorRestrictionEffect extends RestrictionEffect {

    public KithkinArmorRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't be blocked by creatures with power 3 or greater";
    }

    public KithkinArmorRestrictionEffect(final KithkinArmorRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null) {
            Permanent enchantedPermanent = game.getPermanent(enchantment.getAttachedTo());
            return enchantedPermanent != null
                    && permanent.getId().equals(enchantedPermanent.getId());
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() < 3;
    }

    @Override
    public KithkinArmorRestrictionEffect copy() {
        return new KithkinArmorRestrictionEffect(this);
    }
}

class KithkinArmorPreventionEffect extends PreventionEffectImpl {

    KithkinArmorPreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "The next time a source of your choice would deal damage to enchanted creature this turn, prevent that damage";
    }

    KithkinArmorPreventionEffect(final KithkinArmorPreventionEffect effect) {
        super(effect);
    }

    @Override
    public KithkinArmorPreventionEffect copy() {
        return new KithkinArmorPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)
                && event instanceof DamageEvent
                && event.getAmount() > 0
                && !this.used) {
            UUID enchantedCreatureId = (UUID) game.getState().getValue(source.getSourceId().toString() + "attachedToPermanent");
            DamageEvent damageEvent = (DamageEvent) event;
            if (enchantedCreatureId != null
                    && event.getTargetId().equals(enchantedCreatureId)
                    && damageEvent.getSourceId().equals(source.getFirstTarget()))      {
                this.used = true;
                this.discard();
                return true;
            }
        }
        return false;
    }
}
