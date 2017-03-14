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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class ToTheSlaughter extends CardImpl {

    public ToTheSlaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target player sacrifices a creature or planeswalker.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(new FilterCreatureOrPlaneswalkerPermanent(), 1, "Target player"),
                new InvertCondition(DeliriumCondition.instance),
                "Target player sacrifices a creature or planeswalker."));

        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead that player sacrifices a creature and a planeswalker.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(new FilterCreaturePermanent(), 1, "Target player"),
                DeliriumCondition.instance,
                "<br><i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, instead that player sacrifices a creature"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SacrificeEffect(new FilterPlaneswalkerPermanent(), 1, "Target player"),
                DeliriumCondition.instance, "and a planeswalker."));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public ToTheSlaughter(final ToTheSlaughter card) {
        super(card);
    }

    @Override
    public ToTheSlaughter copy() {
        return new ToTheSlaughter(this);
    }
}
