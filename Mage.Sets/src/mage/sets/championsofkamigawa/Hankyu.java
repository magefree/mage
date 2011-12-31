/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.counters.common.AimCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class Hankyu extends CardImpl<Hankyu> {

    public Hankyu(UUID ownerId) {
        super(ownerId, 253, "Hankyu", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Equipment");

        /* Equipped creature has "{T}: Put an aim counter on Hankyu" and */
        SimpleActivatedAbility ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HankyuAddCounterEffect(this.getId()), new TapSourceCost());
        ability1.setSourceId(this.getId()); // to know where to put the counters on
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability1, AttachmentType.EQUIPMENT)));
        
        /* "{T}, Remove all aim counters from Hankyu: This creature deals 
         * damage to target creature or player equal to the number of 
         * aim counters removed this way." */
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HankyuDealsDamageEffect(), new TapSourceCost());
        ability2.addCost(new HankyuCountersSourceCost(this.getId()));
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.EQUIPMENT)));
       
        // Equip {4} ({4}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    public Hankyu(final Hankyu card) {
        super(card);
    }

    @Override
    public Hankyu copy() {
        return new Hankyu(this);
    }
}

class HankyuAddCounterEffect extends OneShotEffect<HankyuAddCounterEffect> {

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
			equipment.addCounters(new AimCounter(), game);
		}
		return true;
	}

	@Override
	public HankyuAddCounterEffect copy() {
		return new HankyuAddCounterEffect(this);
	}


}


class HankyuDealsDamageEffect extends OneShotEffect<HankyuDealsDamageEffect> {

    public HankyuDealsDamageEffect() {
        super(Outcome.Damage);
        staticText = "This creature deals damage to target creature or player equal to the number of aim counters removed this way";
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
                        permanent.damage(damageAmount, source.getSourceId(), game, true, false);
                    }
                    Player player = game.getPlayer(source.getFirstTarget());
                    if (player != null) {
                        player.damage(damageAmount, source.getSourceId(), game, false, true);
                    }
            }
            return true;
        }
        return false;
    }

}

class HankyuCountersSourceCost extends CostImpl<HankyuCountersSourceCost> {

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
	public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
			return true;
	}

	@Override
	public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
		Permanent equipment = game.getPermanent(this.effectGivingEquipmentId);
                this.removedCounters = equipment.getCounters().getCount(CounterType.AIM);
		if (equipment != null && this.removedCounters > 0) {
			equipment.removeCounters("aim", this.removedCounters, game);
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