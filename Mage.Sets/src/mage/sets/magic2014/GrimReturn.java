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
package mage.sets.magic2014;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author LevelX2
 */
public class GrimReturn extends CardImpl<GrimReturn> {

    private static final String textFilter = "creature card in a graveyard that was put there from the battlefield this turn";

    public GrimReturn(UUID ownerId) {
        super(ownerId, 100, "Grim Return", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "M14";

        this.color.setBlack(true);

        // Choose target creature card in a graveyard that was put there from the battlefield this turn. Put that card onto the battlefield under your control.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Choose target creature card in a graveyard that was put there from the battlefield this turn. Put that card onto the battlefield under your control");
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard(textFilter)));
        this.addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    public GrimReturn(final GrimReturn card) {
        super(card);
    }

    @Override
    public GrimReturn copy() {
        return new GrimReturn(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get("CardsPutIntoGraveyardWatcher");
        if (watcher != null) {
            FilterCard filter = new FilterCreatureCard(textFilter);
            List<CardIdPredicate> uuidPredicates = new ArrayList<>();
            for (UUID uuid :watcher.getCardsPutToGraveyardFromBattlefield()) {
                uuidPredicates.add(new CardIdPredicate(uuid));
            }
            filter.add(Predicates.or(uuidPredicates));
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInGraveyard(filter));
        }

    }
}
