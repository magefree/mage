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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.util.functions.AbilityApplier;

/**
 *
 * @author LevelX2
 */
public class ProgenitorMimic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("no Token");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ProgenitorMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{U}");
        this.subtype.add("Shapeshifter");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield
        // except it gains "At the beginning of your upkeep, if this creature isn't a token,
        // create a token that's a copy of this creature."
        Effect effect = new CreateTokenCopySourceEffect();
        effect.setText("create a token that's a copy of this creature");

        AbilityApplier applier = new AbilityApplier(
                new ConditionalTriggeredAbility(
                        new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false),
                        new SourceMatchesFilterCondition(filter),
                        "At the beginning of your upkeep, if this creature isn't a token, create a token that's a copy of this creature.")
        );
        effect = new CopyPermanentEffect(applier);
        effect.setText("as a copy of any creature on the battlefield except it gains \"At the beginning of your upkeep, if this creature isn't a token, create a token that's a copy of this creature.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public ProgenitorMimic(final ProgenitorMimic card) {
        super(card);
    }

    @Override
    public ProgenitorMimic copy() {
        return new ProgenitorMimic(this);
    }
}
