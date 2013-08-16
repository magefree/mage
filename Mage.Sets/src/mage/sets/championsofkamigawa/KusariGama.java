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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class KusariGama extends CardImpl<KusariGama> {

    public KusariGama(UUID ownerId) {
        super(ownerId, 260, "Kusari-Gama", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Equipment");

        // Equipped creature has "{2}: This creature gets +1/+0 until end of turn."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn), new GenericManaCost(2));
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT);
        effect.setText("Equipped creature has \"{2}: This creature gets +1/+0 until end of turn.\"");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
        // Whenever equipped creature deals damage to a blocking creature, Kusari-Gama deals that much damage to each other creature defending player controls.
        this.addAbility(new KusariGamaAbility());
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    public KusariGama(final KusariGama card) {
        super(card);
    }

    @Override
    public KusariGama copy() {
        return new KusariGama(this);
    }
}

class KusariGamaAbility extends TriggeredAbilityImpl<KusariGamaAbility> {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature();

    public KusariGamaAbility() {
        super(Zone.BATTLEFIELD, new KusariGamaDamageEffect());
    }

    public KusariGamaAbility(final KusariGamaAbility ability) {
        super(ability);
    }

    @Override
    public KusariGamaAbility copy() {
        return new KusariGamaAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedEvent) {
            Permanent sourcePermanet = game.getPermanent(event.getSourceId());
            Permanent targetPermanet = game.getPermanent(event.getTargetId());
            if (sourcePermanet != null && targetPermanet != null && sourcePermanet.getAttachments().contains(this.getSourceId()) && filter.match(targetPermanet, game)) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                this.getEffects().get(0).setValue("damagedCreatureId", targetPermanet.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals damage to a blocking creature, {this} deals that much damage to each other creature defending player controls.";
    }
}

class KusariGamaDamageEffect extends OneShotEffect<KusariGamaDamageEffect> {

    public KusariGamaDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals that much damage to each other creature defending player controls";
    }

    public KusariGamaDamageEffect(final KusariGamaDamageEffect effect) {
        super(effect);
    }

    @Override
    public KusariGamaDamageEffect copy() {
        return new KusariGamaDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) this.getValue("damageAmount");
        if (damage != null && damage.intValue() > 0) {
            UUID damagedCreatureId = (UUID) this.getValue("damagedCreatureId");
            Permanent creature = game.getPermanent(damagedCreatureId);
            if (creature == null) {
                creature = (Permanent) game.getLastKnownInformation(damagedCreatureId, Zone.BATTLEFIELD);
            }
            if (creature != null) {
                for (UUID blockerId : game.getCombat().getBlockers()) {
                    if (!blockerId.equals(damagedCreatureId)) {
                        Permanent blockingCreature = game.getPermanent(blockerId);
                        if (blockingCreature != null && blockingCreature.getControllerId().equals(creature.getControllerId())) {
                            blockingCreature.damage(damage, source.getSourceId(), game, true, false);
                        }
                    }
                }
            }
        }
        return false;
    }
}
