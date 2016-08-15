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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class VexingScuttler extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public VexingScuttler(UUID ownerId) {
        super(ownerId, 11, "Vexing Scuttler", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{8}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Eldrazi");
        this.subtype.add("Crab");
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Emerge {6}{U}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{6}{U}")));

        // When you cast Vexing Scuttler, you may return target instant or sorcery card from your graveyard to your hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("you may return target instant or sorcery card from your graveyard to your hand");
        Ability ability = new CastSourceTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    public VexingScuttler(final VexingScuttler card) {
        super(card);
    }

    @Override
    public VexingScuttler copy() {
        return new VexingScuttler(this);
    }
}
