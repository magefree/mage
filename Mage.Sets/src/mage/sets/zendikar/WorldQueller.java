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
package mage.sets.zendikar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class WorldQueller extends CardImpl<WorldQueller> {

    public WorldQueller(UUID ownerId) {
        super(ownerId, 39, "World Queller", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Avatar");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you may choose a card type. If you do, each player sacrifices a permanent of that type.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WorldQuellerEffect(), TargetController.YOU, true));

    }

    public WorldQueller(final WorldQueller card) {
        super(card);
    }

    @Override
    public WorldQueller copy() {
        return new WorldQueller(this);
    }
}

class WorldQuellerEffect extends OneShotEffect<WorldQuellerEffect> {

    private static final HashSet<String> choice = new HashSet<String>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.LAND.toString());
        choice.add(CardType.ENCHANTMENT.toString());
        choice.add(CardType.INSTANT.toString());
        choice.add(CardType.SORCERY.toString());
        choice.add(CardType.PLANESWALKER.toString());
        choice.add(CardType.TRIBAL.toString());
    }

    public WorldQuellerEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "you may choose a card type. If you do, each player sacrifices a permanent of that type";
    }

    public WorldQuellerEffect(final WorldQuellerEffect effect) {
        super(effect);
    }

    @Override
    public WorldQuellerEffect copy() {
        return new WorldQuellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<Card>();
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice);
            while (!you.choose(outcome.Neutral, choiceImpl, game));
            CardType type = null;
            String choosenType = choiceImpl.getChoice();

            if (choosenType.equals(CardType.ARTIFACT.toString())) {
                type = CardType.ARTIFACT;
            } else if (choosenType.equals(CardType.LAND.toString())) {
                type = CardType.LAND;
            } else if (choosenType.equals(CardType.CREATURE.toString())) {
                type = CardType.CREATURE;
            } else if (choosenType.equals(CardType.ENCHANTMENT.toString())) {
                type = CardType.ENCHANTMENT;
            } else if (choosenType.equals(CardType.INSTANT.toString())) {
                type = CardType.INSTANT;
            } else if (choosenType.equals(CardType.SORCERY.toString())) {
                type = CardType.SORCERY;
            } else if (choosenType.equals(CardType.PLANESWALKER.toString())) {
                type = CardType.PLANESWALKER;
            } else if (choosenType.equals(CardType.TRIBAL.toString())) {
                type = CardType.TRIBAL;
            }

            FilterPermanent filter = new FilterPermanent();
            filter.add(new CardTypePredicate(type));

            System.out.println("The type chosen is " + type.toString());

            TargetPermanent target = new TargetControlledPermanent(1, 1, filter, false);
            target.setRequired(true);
            target.setNotTarget(true);

            // you always go first
            if (target.canChoose(you.getId(), game)) {
                while (!target.isChosen() && target.canChoose(you.getId(), game)) {
                    you.choose(Constants.Outcome.Sacrifice, target, source.getId(), game);
                }
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    chosen.add(permanent);
                }
            }

            target.clearChosen();

            // opponents follow
            for (UUID playerId : game.getPlayerList()) {
                if (playerId != you.getId()) {
                    Player player = game.getPlayer(playerId);
                    if (target.canChoose(playerId, game)) {
                        while (!target.isChosen() && target.canChoose(playerId, game)) {
                            player.choose(Constants.Outcome.Sacrifice, target, source.getId(), game);
                        }
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            chosen.add(permanent);
                        }
                        target.clearChosen();
                    }
                }
            }

            // all chosen permanents are sacrificed together
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                if (chosen.contains(permanent)) {
                    permanent.sacrifice(source.getId(), game);
                }
            }
            return true;
        }
        return false;
    }
}
