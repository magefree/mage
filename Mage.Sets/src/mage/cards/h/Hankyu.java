package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class Hankyu extends CardImpl {

    public Hankyu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        /* Equipped creature has "{T}: Put an aim counter on Hankyu" and */
        SimpleActivatedAbility ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HankyuAddCounterEffect(this.getId()), new TapSourceCost());
        ability1.setSourceId(this.getId()); // to know where to put the counters on
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability1, AttachmentType.EQUIPMENT)));

        /* "{T}, Remove all aim counters from Hankyu: This creature deals
         * damage to any target equal to the number of
         * aim counters removed this way." */
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HankyuDealsDamageEffect(), new TapSourceCost());
        ability2.addCost(new HankyuCountersSourceCost(this.getId()));
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.EQUIPMENT)));

        // Equip {4} ({4}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    private Hankyu(final Hankyu card) {
        super(card);
    }

    @Override
    public Hankyu copy() {
        return new Hankyu(this);
    }
}

class HankyuAddCounterEffect extends OneShotEffect {

    private UUID effectGivingEquipmentId;

    public HankyuAddCounterEffect(UUID effectGivingEquipmentId) {
        super(Outcome.Benefit);
        this.effectGivingEquipmentId = effectGivingEquipmentId;
        staticText = "Put an aim counter on Hankyu";
    }

    public HankyuAddCounterEffect(final HankyuAddCounterEffect effect) {
        super(effect);
        this.effectGivingEquipmentId = effect.effectGivingEquipmentId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(this.effectGivingEquipmentId);
        if (equipment != null) {
            equipment.addCounters(CounterType.AIM.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }

    @Override
    public HankyuAddCounterEffect copy() {
        return new HankyuAddCounterEffect(this);
    }


}


class HankyuDealsDamageEffect extends OneShotEffect {

    public HankyuDealsDamageEffect() {
        super(Outcome.Damage);
        staticText = "This creature deals damage to any target equal to the number of aim counters removed this way";
    }

    public HankyuDealsDamageEffect(final HankyuDealsDamageEffect effect) {
        super(effect);
    }

    @Override
    public HankyuDealsDamageEffect copy() {
        return new HankyuDealsDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // get the number of removed counters as damage amount
        HankyuCountersSourceCost cost = (HankyuCountersSourceCost) source.getCosts().get(1);
        if (cost != null) {
            int damageAmount = cost.getRemovedCounters();
            if (damageAmount > 0) {

                Permanent permanent = game.getPermanent(source.getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damageAmount, source.getSourceId(), source, game, false, true);
                }
                Player player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    player.damage(damageAmount, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }

}

class HankyuCountersSourceCost extends CostImpl {

    private int removedCounters;
    private UUID effectGivingEquipmentId;

    public HankyuCountersSourceCost(UUID effectGivingEquipmentId) {
        super();
        this.removedCounters = 0;
        this.effectGivingEquipmentId = effectGivingEquipmentId;
        this.text = "Remove all aim counters from Hankyu";
    }

    public HankyuCountersSourceCost(HankyuCountersSourceCost cost) {
        super(cost);
        this.effectGivingEquipmentId = cost.effectGivingEquipmentId;
        this.removedCounters = cost.removedCounters;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent equipment = game.getPermanent(this.effectGivingEquipmentId);
        if (equipment != null) {
            this.removedCounters = equipment.getCounters(game).getCount(CounterType.AIM);
            if (this.removedCounters > 0) {
                equipment.removeCounters("aim", this.removedCounters, source, game);
            }
        }
        this.paid = true;
        return true;
    }

    @Override
    public HankyuCountersSourceCost copy() {
        return new HankyuCountersSourceCost(this);
    }

    public int getRemovedCounters() {
        return this.removedCounters;
    }
}