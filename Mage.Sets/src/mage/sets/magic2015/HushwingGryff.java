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
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class HushwingGryff extends CardImpl {

    public HushwingGryff(UUID ownerId) {
        super(ownerId, 15, "Hushwing Gryff", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "M15";
        this.subtype.add("Hippogriff");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HushwingGryffEffect()));
    }

    public HushwingGryff(final HushwingGryff card) {
        super(card);
    }

    @Override
    public HushwingGryff copy() {
        return new HushwingGryff(this);
    }
}

class HushwingGryffEffect extends ContinuousRuleModifyingEffectImpl {

    HushwingGryffEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, true);
        staticText = "Creatures entering the battlefield don't cause abilities to trigger";
    }

    HushwingGryffEffect(final HushwingGryffEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (mageObject != null && sourceObject != null) {
            return sourceObject.getLogName() + " prevented ability of " + mageObject.getLogName() + " to trigger";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if (ability != null && AbilityType.TRIGGERED.equals(ability.getAbilityType())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getCardType().contains(CardType.CREATURE)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HushwingGryffEffect copy() {
        return new HushwingGryffEffect(this);
    }

}
