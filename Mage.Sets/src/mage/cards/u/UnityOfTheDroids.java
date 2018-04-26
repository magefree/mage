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
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class UnityOfTheDroids extends CardImpl {

    private static final FilterCreaturePermanent artifactCreatureFilter = new FilterCreaturePermanent("artifact creature");
    private static final FilterCreaturePermanent nonArtifactCreatureFilter = new FilterCreaturePermanent("nonartifact creature");

    static {
        artifactCreatureFilter.add(new CardTypePredicate(CardType.ARTIFACT));
        nonArtifactCreatureFilter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));

    }

    public UnityOfTheDroids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{B}");

        // Choose one - Prevent all damage that would be dealt to target artifact creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(artifactCreatureFilter));

        //   Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        Mode mode = new Mode();
        mode.getEffects().add(new LookLibraryAndPickControllerEffect(new StaticValue(4), false, new StaticValue(1), new FilterCard(), Zone.GRAVEYARD, false, false));
        this.getSpellAbility().addMode(mode);

        //   Destroy target nonartifact creature.
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetCreaturePermanent(nonArtifactCreatureFilter));
        this.getSpellAbility().addMode(mode);
    }

    public UnityOfTheDroids(final UnityOfTheDroids card) {
        super(card);
    }

    @Override
    public UnityOfTheDroids copy() {
        return new UnityOfTheDroids(this);
    }
}
