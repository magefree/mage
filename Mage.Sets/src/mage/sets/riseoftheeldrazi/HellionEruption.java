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
package mage.sets.riseoftheeldrazi;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public class HellionEruption extends CardImpl<HellionEruption> {

    public HellionEruption(UUID ownerId) {
        super(ownerId, 151, "Hellion Eruption", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // Sacrifice all creatures you control, then put that many 4/4 red Hellion creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new HellionEruptionEffect());
    }

    public HellionEruption(final HellionEruption card) {
        super(card);
    }

    @Override
    public HellionEruption copy() {
        return new HellionEruption(this);
    }
}

class HellionEruptionEffect extends OneShotEffect<HellionEruptionEffect> {

    public HellionEruptionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Sacrifice all creatures you control, then put that many 4/4 red Hellion creature tokens onto the battlefield";
    }

    public HellionEruptionEffect(final HellionEruptionEffect effect) {
        super(effect);
    }

    @Override
    public HellionEruptionEffect copy() {
        return new HellionEruptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source.getSourceId(), game);
        }
        (new HellionToken()).putOntoBattlefield(permanents.size(), game, source.getSourceId(), source.getControllerId());
        return true;
    }
}

class HellionToken extends Token {

    HellionToken() {
        super("Hellion", "4/4 red Hellion creature token");
        this.cardType.add(CardType.CREATURE);
        this.color.setRed(true);
        this.subtype.add("Hellion");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }
}