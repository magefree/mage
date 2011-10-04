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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class SeshiroTheAnointed extends CardImpl<SeshiroTheAnointed> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Snakes");

    static {
        filter.getSubtype().add("Snake");
    }

    public SeshiroTheAnointed(UUID ownerId) {
        super(ownerId, 241, "Seshiro the Anointed", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Snake");
        this.subtype.add("Monk");
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Constants.Duration.WhileOnBattlefield, filter, true)));
        this.addAbility(new SeshiroTheAnointedAbility());
    }

    public SeshiroTheAnointed(final SeshiroTheAnointed card) {
        super(card);
    }

    @Override
    public SeshiroTheAnointed copy() {
        return new SeshiroTheAnointed(this);
    }

}

class SeshiroTheAnointedAbility extends TriggeredAbilityImpl<SeshiroTheAnointedAbility> {

    public SeshiroTheAnointedAbility() {
        super(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), true);
    }

    public SeshiroTheAnointedAbility(final SeshiroTheAnointedAbility ability) {
        super(ability);
    }

    @Override
    public SeshiroTheAnointedAbility copy() {
        return new SeshiroTheAnointedAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.hasSubtype("Snake") && p.getControllerId().equals(controllerId)) {
			    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Snake you control deals combat damage to a player, you may draw a card.";
    }
}