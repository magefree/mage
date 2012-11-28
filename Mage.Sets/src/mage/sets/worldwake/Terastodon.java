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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElephantToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Terastodon extends CardImpl<Terastodon> {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public Terastodon(UUID ownerId) {
        super(ownerId, 115, "Terastodon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Elephant");

        this.color.setGreen(true);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When Terastodon enters the battlefield, you may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller puts a 3/3 green Elephant creature token onto the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TerastodonEffect(), true);
        ability.addTarget(new TargetPermanent(0, 3, filter, true));
        this.addAbility(ability);
    }

    public Terastodon(final Terastodon card) {
        super(card);
    }

    @Override
    public Terastodon copy() {
        return new Terastodon(this);
    }
}

class TerastodonEffect extends OneShotEffect<TerastodonEffect> {

    public TerastodonEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "you may destroy up to three target noncreature permanents. For each permanent put into a graveyard this way, its controller puts a 3/3 green Elephant creature token onto the battlefield";
    }

    public TerastodonEffect(final TerastodonEffect effect) {
        super(effect);
    }

    @Override
    public TerastodonEffect copy() {
        return new TerastodonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source.getId(), game, false)) {
                    if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                        Player controller = game.getPlayer(permanent.getControllerId());
                        ElephantToken elephantToken = new ElephantToken();
                        elephantToken.putOntoBattlefield(1, game, source.getSourceId(), controller.getId());
                    }
                }
            }
        }
        return true;
    }
}
