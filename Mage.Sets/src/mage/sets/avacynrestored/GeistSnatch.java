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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class GeistSnatch extends CardImpl<GeistSnatch> {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GeistSnatch(UUID ownerId) {
        super(ownerId, 55, "Geist Snatch", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "AVR";

        this.color.setBlue(true);

        // Counter target creature spell. Put a 1/1 blue Spirit creature token with flying onto the battlefield.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new GeistSnatchCounterTargetEffect());
    }

    public GeistSnatch(final GeistSnatch card) {
        super(card);
    }

    @Override
    public GeistSnatch copy() {
        return new GeistSnatch(this);
    }
}

class GeistSnatchCounterTargetEffect extends OneShotEffect<GeistSnatchCounterTargetEffect> {

    public GeistSnatchCounterTargetEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Counter target creature spell. Put a 1/1 blue Spirit creature token with flying onto the battlefield";
    }

    public GeistSnatchCounterTargetEffect(final GeistSnatchCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public GeistSnatchCounterTargetEffect copy() {
        return new GeistSnatchCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        Token token = new SpiritBlueToken();
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}

class SpiritBlueToken extends Token {

    public SpiritBlueToken() {
        super("Spirit", "1/1 blue Spirit creature token with flying");
        cardType.add(Constants.CardType.CREATURE);
        subtype.add("Spirit");
        color.setBlue(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }
}
