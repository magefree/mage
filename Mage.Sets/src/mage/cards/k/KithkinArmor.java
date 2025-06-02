package mage.cards.k;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetSource;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

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
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(new KithkinArmorRestrictionEffect()));

        // Sacrifice Kithkin Armor: The next time a source of your choice would deal damage to enchanted creature this turn, prevent that damage.
        Ability protectionAbility = new SimpleActivatedAbility(
                new KithkinArmorPreventionEffect(),
                new KithkinArmorCost());
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

    private KithkinArmorCost(final KithkinArmorCost cost) {
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

    KithkinArmorRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't be blocked by creatures with power 3 or greater";
    }

    private KithkinArmorRestrictionEffect(final KithkinArmorRestrictionEffect effect) {
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

    private MageObjectReference mageObjectReference;
    KithkinArmorPreventionEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        staticText = "The next time a source of your choice would deal damage to enchanted creature this turn, prevent that damage";
    }

    private KithkinArmorPreventionEffect(final KithkinArmorPreventionEffect effect) {
        super(effect);
        mageObjectReference = effect.mageObjectReference;
    }

    @Override
    public KithkinArmorPreventionEffect copy() {
        return new KithkinArmorPreventionEffect(this);
    }
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        TargetSource target = new TargetSource();
        target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        mageObjectReference = new MageObjectReference(target.getFirstTarget(), game);
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
