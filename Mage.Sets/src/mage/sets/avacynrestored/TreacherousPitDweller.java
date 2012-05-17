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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noxx
 */
public class TreacherousPitDweller extends CardImpl<TreacherousPitDweller> {

    public TreacherousPitDweller(UUID ownerId) {
        super(ownerId, 121, "Treacherous Pit-Dweller", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Undying
        this.addAbility(new UndyingAbility());

        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        this.addAbility(new TreacherousPitDwellerTriggeredAbility());
    }

    public TreacherousPitDweller(final TreacherousPitDweller card) {
        super(card);
    }

    @Override
    public TreacherousPitDweller copy() {
        return new TreacherousPitDweller(this);
    }
}

class TreacherousPitDwellerTriggeredAbility extends ZoneChangeTriggeredAbility<TreacherousPitDwellerTriggeredAbility> {

    private static final String ruleText = "When {this} enters the battlefield from a graveyard, ";

    public TreacherousPitDwellerTriggeredAbility() {
        super(Constants.Zone.GRAVEYARD, Constants.Zone.BATTLEFIELD, new TreacherousPitDwellerEffect(), ruleText, false);
        addTarget(new TargetOpponent(true));
    }

    public TreacherousPitDwellerTriggeredAbility(final TreacherousPitDwellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TreacherousPitDwellerTriggeredAbility copy() {
        return new TreacherousPitDwellerTriggeredAbility(this);
    }
}

class TreacherousPitDwellerEffect extends ContinuousEffectImpl<TreacherousPitDwellerEffect> {

    public TreacherousPitDwellerEffect() {
        super(Constants.Duration.Custom, Constants.Layer.ControlChangingEffects_2, Constants.SubLayer.NA, Constants.Outcome.GainControl);
        staticText = "Target opponent gains control of {this}";
    }

    public TreacherousPitDwellerEffect(final TreacherousPitDwellerEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousPitDwellerEffect copy() {
        return new TreacherousPitDwellerEffect(this);
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
