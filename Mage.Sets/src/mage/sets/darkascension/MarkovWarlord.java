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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class MarkovWarlord extends CardImpl<MarkovWarlord> {

    public MarkovWarlord(UUID ownerId) {
        super(ownerId, 97, "Markov Warlord", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(HasteAbility.getInstance());
        // When Markov Warlord enters the battlefield, up to two target creatures can't block this turn.
        EntersBattlefieldAbility ability = new EntersBattlefieldAbility(new MarkovWarlordEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

    }

    public MarkovWarlord(final MarkovWarlord card) {
        super(card);
    }

    @Override
    public MarkovWarlord copy() {
        return new MarkovWarlord(this);
    }
}

class MarkovWarlordEffect extends GainAbilityTargetEffect {

    public MarkovWarlordEffect() {
        super(CantBlockAbility.getInstance(), Duration.EndOfTurn);
        staticText = "Up to two target creatures can't block this turn";
    }

    public MarkovWarlordEffect(final MarkovWarlordEffect effect) {
        super(effect);
    }

    @Override
    public MarkovWarlordEffect copy() {
        return new MarkovWarlordEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }
}
