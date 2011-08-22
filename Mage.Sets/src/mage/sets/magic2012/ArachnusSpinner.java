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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class ArachnusSpinner extends CardImpl<ArachnusSpinner> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Spider");

    static {
        filter.getSubtype().add("Spider");
        filter.setTapped(false);
        filter.setUseTapped(true);
    }

    public ArachnusSpinner(UUID ownerId) {
        super(ownerId, 162, "Arachnus Spinner", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "M12";
        this.subtype.add("Spider");

        this.color.setGreen(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        this.addAbility(ReachAbility.getInstance());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ArachnusSpinnerEffect(),
                new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ArachnusSpinner(final ArachnusSpinner card) {
        super(card);
    }

    @Override
    public ArachnusSpinner copy() {
        return new ArachnusSpinner(this);
    }
}

class ArachnusSpinnerEffect extends OneShotEffect<ArachnusSpinnerEffect> {

    public ArachnusSpinnerEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Search your graveyard and/or library for a card named Arachnus Web and put it onto the battlefield attached to target creature. If you search your library this way, shuffle it";
    }

    public ArachnusSpinnerEffect(final ArachnusSpinnerEffect effect) {
        super(effect);
    }

    @Override
    public ArachnusSpinnerEffect copy() {
        return new ArachnusSpinnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterCard filter = new FilterCard("card named Arachnus Web");
        filter.getName().add("Arachnus Web");

        Card card = null;
        Zone zone = null;
        if (player.chooseUse(Outcome.Neutral, "Search your graveyard for Arachnus Web?", game)) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
            if (player.choose(Outcome.PutCardInPlay, player.getGraveyard(), target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    player.removeFromGraveyard(card, game);
                    zone = Zone.GRAVEYARD;
                }
            }
        }
        if (card == null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (player.searchLibrary(target, game)) {
                card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    player.removeFromLibrary(card, game);
                    zone = Zone.LIBRARY;
                }
            }
        }
        if (card != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                card.putOntoBattlefield(game, zone, source.getSourceId(), source.getControllerId());
                return permanent.addAttachment(card.getId(), game);
            }
        }
        return false;
    }
}
