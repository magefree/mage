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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class ThopterAssembly extends CardImpl<ThopterAssembly> {

    public ThopterAssembly(UUID ownerId) {
        super(ownerId, 140, "Thopter Assembly", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Thopter");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ThopterAssemblyTriggeredAbility());
    }

    public ThopterAssembly(final ThopterAssembly card) {
        super(card);
    }

    @Override
    public ThopterAssembly copy() {
        return new ThopterAssembly(this);
    }
}

class ThopterAssemblyTriggeredAbility extends TriggeredAbilityImpl<ThopterAssemblyTriggeredAbility> {
    ThopterAssemblyTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ReturnToHandSourceEffect());
        this.addEffect(new CreateTokenEffect(new ThopterToken(), 5));
    }

    ThopterAssemblyTriggeredAbility(final ThopterAssemblyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThopterAssemblyTriggeredAbility copy() {
        return new ThopterAssemblyTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            FilterPermanent filter = new FilterPermanent();
            filter.getSubtype().add("Thopter");
            filter.setScopeSubtype(Filter.ComparisonScope.Any);
            filter.setAnother(true);
            if (!game.getBattlefield().contains(filter, controllerId, 1, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, if you control no Thopters other than {this}, return {this} to its owner's hand and put five 1/1 colorless Thopter artifact creature tokens with flying onto the battlefield";
    }
}

class ThopterToken extends Token {
    ThopterToken() {
        super("Thopter", "a 1/1 colorless Thopter artifact creature token with flying");
        cardType.add(Constants.CardType.CREATURE);
        cardType.add(Constants.CardType.ARTIFACT);
        subtype.add("Thopter");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}