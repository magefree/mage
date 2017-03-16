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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author anonymous
 */
public class HuntingWilds extends CardImpl {

    public HuntingWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Kicker {3}{G}
        this.addAbility(new KickerAbility("{3}{G}"));

        FilterLandCard filter = new FilterLandCard("Forest card");
        filter.add(new SubtypePredicate("Forest"));

        // Search your library for up to two Forest cards and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), true));

        // If Hunting Wilds was kicked, untap all Forests put onto the battlefield this way.
        // They become 3/3 green creatures with haste that are still lands.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new HuntingWildsEffect(), KickedCondition.instance));
    }

    public HuntingWilds(final HuntingWilds card) {
        super(card);
    }

    @Override
    public HuntingWilds copy() {
        return new HuntingWilds(this);
    }
}

class HuntingWildsEffect extends OneShotEffect {

    public HuntingWildsEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "Untap all Forests put onto the battlefield this way. They become 3/3 green creatures with haste that are still lands";
    }

    public HuntingWildsEffect(final HuntingWildsEffect effect) {
        super(effect);
    }

    @Override
    public HuntingWildsEffect copy() {
        return new HuntingWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Effect sourceEffect : source.getEffects()) {
            if (sourceEffect instanceof SearchLibraryPutInPlayEffect) {
                Cards foundCards = new CardsImpl(((SearchLibraryPutInPlayEffect) sourceEffect).getTargets());
                if (!foundCards.isEmpty()) {
                    FixedTargets fixedTargets = new FixedTargets(foundCards, game);
                    UntapTargetEffect untapEffect = new UntapTargetEffect();
                    untapEffect.setTargetPointer(fixedTargets);
                    untapEffect.apply(game, source);

                    BecomesCreatureTargetEffect becomesCreatureEffect = new BecomesCreatureTargetEffect(new HuntingWildsToken(), false, true, Duration.Custom);
                    becomesCreatureEffect.setTargetPointer(fixedTargets);
                    game.addEffect(becomesCreatureEffect, source);
                }
                return true;
            }
        }
        return false;
    }
}

class HuntingWildsToken extends Token {

    public HuntingWildsToken() {
        super("", "3/3 green creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
    }
}
