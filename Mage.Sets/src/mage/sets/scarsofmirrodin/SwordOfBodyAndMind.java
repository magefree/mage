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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class SwordOfBodyAndMind extends CardImpl<SwordOfBodyAndMind> {

    private static final FilterCard filter = new FilterCard("green and from blue");

    static {
        filter.setUseColor(true);
        filter.getColor().setBlue(true);
        filter.getColor().setGreen(true);
        filter.setScopeColor(Filter.ComparisonScope.Any);
    }


    public SwordOfBodyAndMind (UUID ownerId) {
        super(ownerId, 208, "Sword of Body and Mind", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Equipment");
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT)));
        this.addAbility(new SwordOfBodyAndMindAbility());
    }

    public SwordOfBodyAndMind (final SwordOfBodyAndMind card) {
        super(card);
    }

    @Override
    public SwordOfBodyAndMind copy() {
        return new SwordOfBodyAndMind(this);
    }
}

class SwordOfBodyAndMindAbility extends TriggeredAbilityImpl<SwordOfBodyAndMindAbility> {

    public SwordOfBodyAndMindAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()));
        this.addEffect(new PutLibraryIntoGraveTargetEffect(10));
        this.addTarget(new TargetPlayer());
    }

    public SwordOfBodyAndMindAbility(final SwordOfBodyAndMindAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfBodyAndMindAbility copy() {
        return new SwordOfBodyAndMindAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                this.targets.get(0).add(event.getPlayerId(), game);
			    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you put a 2/2 green Wolf creature token onto the battlefield and that player puts the top ten cards of his or her library into his or her graveyard.";
    }
}

class WolfToken extends Token {
    public WolfToken() {
        super("Wolf", "a 2/2 green Wolf creature token");
        cardType.add(CardType.CREATURE);
		color = ObjectColor.GREEN;
		subtype.add("Wolf");
		power = new MageInt(2);
		toughness = new MageInt(2);
    }
}