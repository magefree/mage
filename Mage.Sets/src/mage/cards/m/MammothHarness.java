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
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class MammothHarness extends CardImpl {

    public MammothHarness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature loses flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LoseAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)));

        // Whenever enchanted creature blocks or becomes blocked by a creature, the other creature gains first strike until end of turn.
        this.addAbility(new MammothHarnessTriggeredAbility());
    }

    public MammothHarness(final MammothHarness card) {
        super(card);
    }

    @Override
    public MammothHarness copy() {
        return new MammothHarness(this);
    }
}

class MammothHarnessTriggeredAbility extends BlocksOrBecomesBlockedTriggeredAbility {

    public MammothHarnessTriggeredAbility() {
        super(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), StaticFilters.FILTER_PERMANENT_CREATURE, false, null, false);
    }

    public MammothHarnessTriggeredAbility(final MammothHarnessTriggeredAbility ability) {
        super(ability);

    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (sourcePermanent != null) {
            Permanent attachedTo = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
            if (sourcePermanent != null) {
                if (event.getSourceId().equals(attachedTo.getId())) {
                    Permanent blocked = game.getPermanent(event.getTargetId());
                    if (blocked != null && filter.match(blocked, game)) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                        return true;
                    }
                }
                if (event.getTargetId().equals(attachedTo.getId())) {
                    Permanent blocker = game.getPermanent(event.getSourceId());
                    if (blocker != null) {
                        this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return " Whenever enchanted creature blocks or becomes blocked by a creature, the other creature gains first strike until end of turn.";
    }

    @Override
    public MammothHarnessTriggeredAbility copy() {
        return new MammothHarnessTriggeredAbility(this);
    }
}
