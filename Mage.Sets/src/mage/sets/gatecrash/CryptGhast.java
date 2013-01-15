/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class CryptGhast extends CardImpl<CryptGhast> {

    public CryptGhast(UUID ownerId) {
        super(ownerId, 61, "Crypt Ghast", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());
        //Whenever you tap a Swamp for mana, you may add {B} to your mana pool.
        this.addAbility(new CryptGhastTriggeredAbility());
    }

    public CryptGhast(final CryptGhast card) {
        super(card);
    }

    @Override
    public CryptGhast copy() {
        return new CryptGhast(this);
    }
}

class CryptGhastTriggeredAbility extends TriggeredManaAbility<CryptGhastTriggeredAbility> {
    
    private final static FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Island");
    
    static {
            filter.add(new SubtypePredicate("Swamp"));
    }

    public CryptGhastTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new BasicManaEffect(Mana.BlackMana));
        this.usesStack = false;
    }

    public CryptGhastTriggeredAbility(CryptGhastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent land = game.getPermanent(event.getTargetId());
        if (event.getType() == GameEvent.EventType.TAPPED_FOR_MANA && land != null && filter.match(land, game)) {
            return true;
        }
        return false;
    }

    @Override
    public CryptGhastTriggeredAbility copy() {
        return new CryptGhastTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a Swamp for mana, you may add {B} to your mana pool";
    }
}