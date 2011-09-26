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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public class GlissaTheTraitor extends CardImpl<GlissaTheTraitor> {
    public GlissaTheTraitor (UUID ownerId) {
        super(ownerId, 96, "Glissa, the Traitor", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{B}{G}{G}");
        this.expansionSetCode = "MBS";
        this.supertype.add("Legendary");
        this.subtype.add("Zombie");
        this.subtype.add("Elf");
		this.color.setBlack(true);
		this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(new GlissaTheTraitorTriggeredAbility());
    }

    public GlissaTheTraitor (final GlissaTheTraitor card) {
        super(card);
    }

    @Override
    public GlissaTheTraitor copy() {
        return new GlissaTheTraitor(this);
    }

}

class GlissaTheTraitorTriggeredAbility extends TriggeredAbilityImpl<GlissaTheTraitorTriggeredAbility> {
    private static final FilterCard filter = new FilterCard("artifact card from your graveyard");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    GlissaTheTraitorTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
        this.addTarget(new TargetCardInYourGraveyard(filter));
    }

    GlissaTheTraitorTriggeredAbility(final GlissaTheTraitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlissaTheTraitorTriggeredAbility copy() {
        return new GlissaTheTraitorTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (p != null && p.getCardType().contains(CardType.CREATURE) && game.getOpponents(this.getControllerId()).contains(p.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls is put into a graveyard from the battlefield, " + super.getRule();
    }
}