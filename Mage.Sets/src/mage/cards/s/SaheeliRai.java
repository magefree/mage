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
package mage.sets.kaladesh;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class SaheeliRai extends CardImpl {

    public SaheeliRai(UUID ownerId) {
        super(ownerId, 186, "Saheeli Rai", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");
        this.expansionSetCode = "KLD";
        this.subtype.add("Saheeli");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Scry 1. Saheeli Rai deals 1 damage to each opponent.
        Effect effect = new ScryEffect(1);
        effect.setText("Scry 1");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.addAbility(ability);

        // -2: Create a token that's a copy of target artifact or creature you control, except it's an artifact in addition to its other types. That token gains haste. Exile it at the beginning of the next end step.
        ability = new LoyaltyAbility(new SaheeliRaiCreateTokenEffect(), -2);
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // -7: Search your library for up to three artifact cards with different names, put them onto the battlefield, then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new SaheeliRaiTarget()), -7));
    }

    public SaheeliRai(final SaheeliRai card) {
        super(card);
    }

    @Override
    public SaheeliRai copy() {
        return new SaheeliRai(this);
    }
}

class SaheeliRaiCreateTokenEffect extends OneShotEffect {

    SaheeliRaiCreateTokenEffect() {
        super(Outcome.Copy);
        this.staticText = "Create a token that's a copy of target artifact or creature you control, except it's an artifact in addition to its other types. That token gains haste. Exile it at the beginning of the next end step";
    }

    SaheeliRaiCreateTokenEffect(final SaheeliRaiCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliRaiCreateTokenEffect copy() {
        return new SaheeliRaiCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copiedPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (copiedPermanent != null) {
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(null, CardType.ARTIFACT, true);
            if (effect.apply(game, source)) {
                for (Permanent copyPermanent : effect.getAddedPermanent()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(copyPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }
        return false;
    }
}

class SaheeliRaiTarget extends TargetCardInLibrary {

    SaheeliRaiTarget() {
        super(0, 3, new FilterArtifactCard("artifact cards with different names"));
    }

    SaheeliRaiTarget(final SaheeliRaiTarget target) {
        super(target);
    }

    @Override
    public SaheeliRaiTarget copy() {
        return new SaheeliRaiTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, game);
        }
        return false;
    }
}
