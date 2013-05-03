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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class ZhurTaaDruid extends CardImpl<ZhurTaaDruid> {

    public ZhurTaaDruid(UUID ownerId) {
        super(ownerId, 120, "Zhur-Taa Druid", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}{G}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Druid");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {G} to your mana pool.
        this.addAbility(new GreenManaAbility());
        // Whenever you tap Zhur-Taa Druid for mana, it deals 1 damage to each opponent.
        this.addAbility(new ZhurTaaDruidAbility());

    }

    public ZhurTaaDruid(final ZhurTaaDruid card) {
        super(card);
    }

    @Override
    public ZhurTaaDruid copy() {
        return new ZhurTaaDruid(this);
    }
}

class ZhurTaaDruidAbility extends TriggeredAbilityImpl<ZhurTaaDruidAbility> {

    public ZhurTaaDruidAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(2, TargetController.OPPONENT));
    }

    public ZhurTaaDruidAbility(final ZhurTaaDruidAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && event.getSourceId().equals(getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever you tap {this} for mana, ").append(super.getRule()).toString() ;
    }

    @Override
    public ZhurTaaDruidAbility copy() {
        return new ZhurTaaDruidAbility(this);
    }

}
