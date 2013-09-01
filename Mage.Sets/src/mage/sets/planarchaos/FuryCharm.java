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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class FuryCharm extends CardImpl<FuryCharm> {

    private static final FilterCard filter = new FilterCard("suspended card");
    static {
        filter.add(new CounterPredicate(CounterType.TIME));
        filter.add(new AbilityPredicate(SuspendAbility.class));
    }

    public FuryCharm(UUID ownerId) {
        super(ownerId, 100, "Fury Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "PLC";

        this.color.setRed(true);

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Destroy target artifact;
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        // or target creature gets +1/+1 and gains trample until end of turn;
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(1,1, Duration.EndOfTurn));
        mode.getEffects().add(new GainAbilityTargetEffect(TrampleAbility.getInstance(),Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or remove two time counters from target permanent or suspended card.
        mode = new Mode();
        Choice targetChoice = new ChoiceImpl();
        targetChoice.setMessage("Choose what to target");
        targetChoice.getChoices().add("Permanent");
        targetChoice.getChoices().add("Suspended Card");
        mode.getChoices().add(targetChoice);
        mode.getEffects().add(new FuryCharmRemoveCounterEffect());
        this.getSpellAbility().getModes().addMode(mode);



    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            for(Effect effect :ability.getEffects()) {
                if (effect instanceof FuryCharmRemoveCounterEffect) {
                    Choice targetChoice = ability.getChoices().get(0);
                    if (targetChoice.getChoice().equals("Permanent")) {
                        ability.addTarget(new TargetCreaturePermanent(true));
                    }
                    if (targetChoice.getChoice().equals("Suspended Card")) {
                        Target target = new TargetCardInExile(1,1, filter, null, true);
                        target.setRequired(true);
                        ability.addTarget(target);
                    }
                }
            }
        }
    }

    public FuryCharm(final FuryCharm card) {
        super(card);
    }

    @Override
    public FuryCharm copy() {
        return new FuryCharm(this);
    }
}

class FuryCharmRemoveCounterEffect extends OneShotEffect<FuryCharmRemoveCounterEffect> {

    public FuryCharmRemoveCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "remove two time counters from target permanent or suspended card";
    }

    public FuryCharmRemoveCounterEffect(final FuryCharmRemoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public FuryCharmRemoveCounterEffect copy() {
        return new FuryCharmRemoveCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.removeCounters(CounterType.TIME.getName(), 2,  game);
            return true;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null) {
            card.removeCounters(CounterType.TIME.getName(), 2,  game);
            return true;
        }
        return false;
    }
}
