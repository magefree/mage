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
package mage.sets.conspiracy;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class MuzzioVisionaryArchitect extends CardImpl {

    public MuzzioVisionaryArchitect(UUID ownerId) {
        super(ownerId, 23, "Muzzio, Visionary Architect", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "CNS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {3}{U}, {tap}: Look at the top X cards of your library, where X is the highest converted mana cost among artifacts you control. You may reveal an artifact card from among them and put it onto the battlefield. Put the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MuzzioVisionaryArchitectEffect(), new ManaCostsImpl("{3}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MuzzioVisionaryArchitect(final MuzzioVisionaryArchitect card) {
        super(card);
    }

    @Override
    public MuzzioVisionaryArchitect copy() {
        return new MuzzioVisionaryArchitect(this);
    }
}

class MuzzioVisionaryArchitectEffect extends OneShotEffect {

    public MuzzioVisionaryArchitectEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top X cards of your library, where X is the highest converted mana cost among artifacts you control. You may reveal an artifact card from among them and put it onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    public MuzzioVisionaryArchitectEffect(final MuzzioVisionaryArchitectEffect effect) {
        super(effect);
    }

    @Override
    public MuzzioVisionaryArchitectEffect copy() {
        return new MuzzioVisionaryArchitectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player == null || sourcePermanent == null) {
            return false;
        }

        int highCMC = 0;
        List<Permanent> controlledArtifacts = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), player.getId(), game);
        for (Permanent permanent : controlledArtifacts) {
            if (permanent.getSpellAbility() != null) {
                int cmc = permanent.getSpellAbility().getManaCosts().convertedManaCost();
                if (cmc > highCMC) {
                    highCMC = cmc;
                }
            }
        }

        Cards cards = new CardsImpl();

        for (int i = 0; i < highCMC; i++) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
            }
        }
        player.lookAtCards(sourcePermanent.getName(), cards, game);

        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterArtifactCard("artifact card to put onto the battlefield"));
            if (target.canChoose(source.getSourceId(), player.getId(), game) && player.choose(Outcome.Benefit, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    player.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    cards.remove(card);
                    player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                }
            }
        }


        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        while (player.isInGame() && cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        }
        return true;
    }
}