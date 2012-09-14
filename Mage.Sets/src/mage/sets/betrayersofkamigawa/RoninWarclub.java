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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RoninWarclub extends CardImpl<RoninWarclub> {

    public RoninWarclub(UUID ownerId) {
        super(ownerId, 158, "Ronin Warclub", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Equipment");
        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1)));
        
        // Whenever a creature enters the battlefield under your control, attach Ronin Warclub to that creature.
        Ability ability = new RoninWarclubTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Equip {5} ({5}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Constants.Outcome.BoostCreature, new GenericManaCost(5)));
    }

    public RoninWarclub(final RoninWarclub card) {
        super(card);
    }

    @Override
    public RoninWarclub copy() {
        return new RoninWarclub(this);
    }
    
    private class RoninWarclubTriggeredAbility extends TriggeredAbilityImpl<RoninWarclubTriggeredAbility> {

        public RoninWarclubTriggeredAbility() {
           super(Constants.Zone.BATTLEFIELD, new RoninWarclubAttachEffect(), false);
        }

        public RoninWarclubTriggeredAbility(RoninWarclubTriggeredAbility ability) {
            super(ability);
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (((ZoneChangeEvent) event).getToZone() == Constants.Zone.BATTLEFIELD
                        && permanent.getCardType().contains(CardType.CREATURE)
                        && (permanent.getControllerId().equals(this.controllerId))) {

                    if (!this.getTargets().isEmpty()) {
                        // remove previous target
                        if (this.getTargets().get(0).getTargets().size() > 0) {
                            this.getTargets().clear();
                            this.addTarget(new TargetCreaturePermanent());
                        }
                        Target target = this.getTargets().get(0);
                        if (target instanceof TargetCreaturePermanent) {
                            target.add(event.getTargetId(), game);
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public RoninWarclubTriggeredAbility copy() {
            return new RoninWarclubTriggeredAbility(this);
        }
    }
    
    private class RoninWarclubAttachEffect extends OneShotEffect<RoninWarclubAttachEffect> {

        public RoninWarclubAttachEffect() {
            super(Constants.Outcome.BoostCreature);
            this.staticText = "Whenever a creature enters the battlefield under your control, attach {this} to that creature";
        }

        public RoninWarclubAttachEffect(final RoninWarclubAttachEffect effect) {
            super(effect);
        }

        @Override
        public RoninWarclubAttachEffect copy() {
            return new RoninWarclubAttachEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                boolean result;
                result = permanent.addAttachment(source.getSourceId(), game);
                return result;
            }
            return false;
        }
        
    }
}
