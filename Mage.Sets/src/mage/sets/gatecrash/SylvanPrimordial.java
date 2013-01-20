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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class SylvanPrimordial extends CardImpl<SylvanPrimordial> {

    public SylvanPrimordial(UUID ownerId) {
        super(ownerId, 136, "Sylvan Primordial", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Avatar");

        this.color.setGreen(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Sylvan Primordial enters the battlefield, for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SylvanPrimordialEffect(),false));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            for(UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    ability.getTargets().clear();
                    FilterPermanent filter = new FilterPermanent(new StringBuilder("noncreature permanent from opponent ").append(opponent.getName()).toString());
                    filter.add(new ControllerIdPredicate(opponentId));
                    filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
                    TargetPermanent target = new TargetPermanent(0,1, filter,false);
                    ability.addTarget(target);
                }
            }
        }
    }

    public SylvanPrimordial(final SylvanPrimordial card) {
        super(card);
    }

    @Override
    public SylvanPrimordial copy() {
        return new SylvanPrimordial(this);
    }
}

class SylvanPrimordialEffect extends OneShotEffect<SylvanPrimordialEffect> {

    private static final FilterLandCard filterForest = new FilterLandCard("Forest");
    static{
        filterForest.add(new SubtypePredicate("Forest"));
    }

    public SylvanPrimordialEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "for each opponent, destroy target noncreature permanent that player controls. For each permanent destroyed this way, search your library for a Forest card and put that card onto the battlefield tapped. Then shuffle your library";
    }

    public SylvanPrimordialEffect(final SylvanPrimordialEffect effect) {
        super(effect);
    }

    @Override
    public SylvanPrimordialEffect copy() {
        return new SylvanPrimordialEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        int destroyedCreatures = 0;
        for (Target target: source.getTargets()) {
            if (target instanceof TargetPermanent) {
                Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                if (targetPermanent != null) {
                    if (targetPermanent.destroy(source.getSourceId(), game, false)) {
                        destroyedCreatures++;
                    }
                }
            }
        }
        if (destroyedCreatures > 0) {
            new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(destroyedCreatures,filterForest), true, true).apply(game, source);
        }
        return result;
    }
}

