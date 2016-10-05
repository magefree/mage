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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.PlayerPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class CollectiveBrutality extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card");
    private static final FilterPlayer filterDiscard = new FilterPlayer("opponent to discard");
    private static final FilterCreaturePermanent filterCreatureMinus = new FilterCreaturePermanent("creature to get -2/-2");
    private static final FilterPlayer filterLoseLife = new FilterPlayer("opponent to lose life");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
        filterDiscard.add(new PlayerPredicate(TargetController.OPPONENT));
        filterLoseLife.add(new PlayerPredicate(TargetController.OPPONENT));
    }

    public CollectiveBrutality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Escalate - Discard a card.
        Cost cost = new DiscardCardCost();
        cost.setText("&mdash; Discard a card");
        this.addAbility(new EscalateAbility(cost));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target opponent reveals his or her hand. You choose an instant or sorcery card from it. That player discards that card.;
        Effect effect = new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY);
        effect.setText("Target opponent reveals his or her hand. You choose an instant or sorcery card from it. That player discards that card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterDiscard));

        // Target creature gets -2/-2 until end of turn.;
        Mode mode = new Mode();
        effect = new BoostTargetEffect(-2, -2, Duration.EndOfTurn);
        effect.setText("Target creature gets -2/-2 until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterCreatureMinus));
        this.getSpellAbility().addMode(mode);

        // Target opponent loses 2 life and you gain 2 life.
        mode = new Mode();
        effect = new LoseLifeTargetEffect(2);
        effect.setText("Target opponent loses 2 life");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetPlayer(1, 1, false, filterLoseLife));
        effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        mode.getEffects().add(effect);
        this.getSpellAbility().addMode(mode);
    }

    public CollectiveBrutality(final CollectiveBrutality card) {
        super(card);
    }

    @Override
    public CollectiveBrutality copy() {
        return new CollectiveBrutality(this);
    }
}
