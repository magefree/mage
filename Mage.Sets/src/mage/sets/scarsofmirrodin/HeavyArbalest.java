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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author North
 */
public class HeavyArbalest extends CardImpl<HeavyArbalest> {

    public HeavyArbalest(UUID ownerId) {
        super(ownerId, 164, "Heavy Arbalest", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Equipment");

        SimpleStaticAbility ability1 = new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapSourceEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability1, AttachmentType.EQUIPMENT)));

        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.EQUIPMENT)));
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    public HeavyArbalest(final HeavyArbalest card) {
        super(card);
    }

    @Override
    public HeavyArbalest copy() {
        return new HeavyArbalest(this);
    }
}

class SkipUntapSourceEffect extends ReplacementEffectImpl<SkipUntapSourceEffect> {

    public SkipUntapSourceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
    }

    public SkipUntapSourceEffect(final SkipUntapSourceEffect effect) {
        super(effect);
    }

    @Override
    public SkipUntapSourceEffect copy() {
        return new SkipUntapSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP
                && event.getType() == EventType.UNTAP
                && event.getTargetId().equals(source.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getText(Ability source) {
        return "Equipped creature doesn't untap during its controller's untap step.";
    }
}
