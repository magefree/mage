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

package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.Metalcraft;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public class PuresteelPaladin extends CardImpl<PuresteelPaladin> {
    private static final FilterPermanent filter = new FilterPermanent("Equipment");

    static {
        filter.getSubtype().add("Equipment");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public PuresteelPaladin(UUID ownerId) {
        super(ownerId, 20, "Puresteel Paladin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new PuresteelPaladinTriggeredAbility());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD,
                                                new ConditionalContinousEffect(
                                                        new GainAbilityControlledEffect(
                                                                new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(0)),
                                                                Constants.Duration.WhileOnBattlefield, filter),
                                                        Metalcraft.getInstance(),
                                                        "Metalcraft - Equipment you control have equip {0} as long as you control three or more artifacts")));
    }

    public PuresteelPaladin(final PuresteelPaladin card) {
        super(card);
    }

    @Override
    public PuresteelPaladin copy() {
        return new PuresteelPaladin(this);
    }
}

class PuresteelPaladinTriggeredAbility extends TriggeredAbilityImpl<PuresteelPaladinTriggeredAbility> {
    PuresteelPaladinTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), true);
    }

    PuresteelPaladinTriggeredAbility(final PuresteelPaladinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PuresteelPaladinTriggeredAbility copy() {
        return new PuresteelPaladinTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getSubtype().contains("Equipment")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an Equipment enters the battlefield under your control, you may draw a card.";
    }
}