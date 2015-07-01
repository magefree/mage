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
package mage.sets.nemesis;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.PutCreatureOnBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author andyfries
 */
public class BelbesPortal extends CardImpl {

    public BelbesPortal(UUID ownerId) {
        super(ownerId, 127, "Belbe's Portal", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "NMS";

        // As Belbe's Portal enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay)));
        // {3}, {tap}: You may put a creature card of the chosen type from your hand onto the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BelbesPortalPutCreatureOnBattlefieldEffect(),
                new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public BelbesPortal(final BelbesPortal card) {
        super(card);
    }

    @Override
    public BelbesPortal copy() {
        return new BelbesPortal(this);
    }
}

class BelbesPortalPutCreatureOnBattlefieldEffect extends OneShotEffect {
    BelbesPortalPutCreatureOnBattlefieldEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "You may put a creature card of the chosen type from your hand onto the battlefield";
    }

    BelbesPortalPutCreatureOnBattlefieldEffect(final BelbesPortalPutCreatureOnBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public BelbesPortalPutCreatureOnBattlefieldEffect copy() {
        return new BelbesPortalPutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            String subtype = (String) game.getState().getValue(permanent.getId() + "_type");
            if (subtype != null) {
                Player player = game.getPlayer(source.getControllerId());
                String choiceText = "Put a " + subtype.toLowerCase() + " creature card from your hand onto the battlefield?";

                if (player != null) {
                    if (player.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
                        FilterCreatureCard creatureTypeFilter = new FilterCreatureCard();
                        creatureTypeFilter.add(new SubtypePredicate(subtype));

                        TargetCardInHand target = new TargetCardInHand(creatureTypeFilter);
                        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}