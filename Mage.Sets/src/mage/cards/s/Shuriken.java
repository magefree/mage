
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class Shuriken extends CardImpl {

    public Shuriken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Shuriken: Shuriken deals 2 damage to target creature. That creature's controller gains control of Shuriken unless it was unattached from a Ninja."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShurikenDamageEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new ShurikenUnattachCost());
        ability.addEffect(new ShurikenControlEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT, Duration.WhileOnBattlefield)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(2)));
    }

    public Shuriken(final Shuriken card) {
        super(card);
    }

    @Override
    public Shuriken copy() {
        return new Shuriken(this);
    }
}

class ShurikenDamageEffect extends OneShotEffect {

    public ShurikenDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "Shuriken deals 2 damage to target creature";
    }

    public ShurikenDamageEffect(final ShurikenDamageEffect effect) {
        super(effect);
    }

    @Override
    public ShurikenDamageEffect copy() {
        return new ShurikenDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = null;
        for(Cost cost : source.getCosts()) {
            if (cost instanceof ShurikenUnattachCost) {
                equipment = ((ShurikenUnattachCost) cost).getEquipment();
                break;
            }
        }
        if (equipment != null) {
            Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (creature != null) {
                creature.damage(2, equipment.getId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}


class ShurikenUnattachCost extends CostImpl {

    Permanent equipment;

    public ShurikenUnattachCost() {
        this.text = "Unattach Shuriken";
    }

    public ShurikenUnattachCost(final ShurikenUnattachCost cost) {
        super(cost);
        this.equipment = cost.equipment;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId :permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getName().equals("Shuriken")) {
                    paid = permanent.removeAttachment(attachmentId, game);
                    if (paid) {
                        equipment = attachment;
                        break;
                    }
                }
            }

        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            for (UUID attachmentId :permanent.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.getName().equals("Shuriken")) {
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public ShurikenUnattachCost copy() {
        return new ShurikenUnattachCost(this);
    }

    public Permanent getEquipment() {
        return equipment;
    }
}

class ShurikenControlEffect extends OneShotEffect {

    public ShurikenControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "That creature's controller gains control of Shuriken unless it was unattached from a Ninja";
    }

    public ShurikenControlEffect(final ShurikenControlEffect effect) {
        super(effect);
    }

    @Override
    public ShurikenControlEffect copy() {
        return new ShurikenControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = null; // TODO: Meanwhile blinked object would be found regardless
        for(Cost cost : source.getCosts()) {
            if (cost instanceof ShurikenUnattachCost) {
                equipment = ((ShurikenUnattachCost) cost).getEquipment();
            }
        }
        if (equipment != null) {
            Permanent creature = game.getPermanent(source.getSourceId());
            if (creature != null) {
                if (!creature.hasSubtype(SubType.NINJA, game)) {
                    Permanent damagedCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                    if (damagedCreature == null) {
                        damagedCreature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
                    }
                    if (damagedCreature != null) {
                        ContinuousEffect effect = new ShurikenGainControlEffect(Duration.EndOfGame, damagedCreature.getControllerId());
                        effect.setTargetPointer(new FixedTarget(equipment.getId()));
                        game.addEffect(effect, source);
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class ShurikenGainControlEffect extends ContinuousEffectImpl {

    UUID controller;

    public ShurikenGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public ShurikenGainControlEffect(final ShurikenGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public ShurikenGainControlEffect copy() {
        return new ShurikenGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null) {
            return permanent.changeControllerId(controller, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of Shuriken";
    }
}
