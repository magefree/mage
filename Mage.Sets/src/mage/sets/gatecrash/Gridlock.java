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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Gridlock extends CardImpl<Gridlock> {
    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents");

    public Gridlock(UUID ownerId) {
        super(ownerId, 36, "Gridlock", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{U}");
        this.expansionSetCode = "GTC";

        this.color.setBlue(true);

        // Tap X target nonland permanents.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        // Correct number of targets will be set in adjustTargets
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1,filter, false));
        
    }

    public Gridlock(final Gridlock card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberToTap = ability.getManaCostsToPay().getX();
            numberToTap = Math.min(game.getBattlefield().count(filter, ability.getSourceId(), ability.getControllerId(), game), numberToTap);
            ability.addTarget(new TargetPermanent(numberToTap, filter));
        }
    }

    @Override
    public Gridlock copy() {
        return new Gridlock(this);
    }
}
