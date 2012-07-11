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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class Glimmerpost extends CardImpl<Glimmerpost> {

    public Glimmerpost (UUID ownerId) {
        super(ownerId, 227, "Glimmerpost", Rarity.COMMON, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "SOM";
        this.subtype.add("Locus");
        this.addAbility(new ColorlessManaAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GlimmerpostEffect()));
    }

    public Glimmerpost (final Glimmerpost card) {
        super(card);
    }

    @Override
    public Glimmerpost copy() {
        return new Glimmerpost(this);
    }

}

class GlimmerpostEffect extends OneShotEffect<GlimmerpostEffect> {
    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new SubtypePredicate("Locus"));
    }

    public GlimmerpostEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "you gain 1 life for each Locus on the battlefield";
    }

    public GlimmerpostEffect(final GlimmerpostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().count(filter, source.getControllerId(), game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game);
            return true;
        }
        return false;
    }

    @Override
    public GlimmerpostEffect copy() {
        return new GlimmerpostEffect(this);
    }

}