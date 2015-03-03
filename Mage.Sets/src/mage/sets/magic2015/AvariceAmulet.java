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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class AvariceAmulet extends CardImpl {

    public AvariceAmulet(UUID ownerId) {
        super(ownerId, 212, "Avarice Amulet", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "M15";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+0
        Effect effect = new BoostEquippedEffect(2, 0);
        effect.setText("Equipped creature gets +2/+0");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        // and has vigilance
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText("and has vigilance");
        ability.addEffect(effect);
        //and "At the beginning of your upkeep, draw a card."
        effect = new GainAbilityAttachedEffect(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1), TargetController.YOU, false), AttachmentType.EQUIPMENT);
        effect.setText("and \"At the beginning of your upkeep, draw a card.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When equipped creature dies, target opponent gains control of Avarice Amulet.
        ability = new DiesAttachedTriggeredAbility(new AvariceAmuletChangeControlEffect(), "equipped creature", false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public AvariceAmulet(final AvariceAmulet card) {
        super(card);
    }

    @Override
    public AvariceAmulet copy() {
        return new AvariceAmulet(this);
    }
}

class AvariceAmuletChangeControlEffect extends ContinuousEffectImpl {

    AvariceAmuletChangeControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    AvariceAmuletChangeControlEffect(final AvariceAmuletChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public AvariceAmuletChangeControlEffect copy() {
        return new AvariceAmuletChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game);
        }
        return false;
    }

}
