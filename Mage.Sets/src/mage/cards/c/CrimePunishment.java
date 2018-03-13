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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInOpponentsGraveyard;

public class CrimePunishment extends SplitCard {

    private static final FilterCard filter = new FilterCard("creature or enchantment card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public CrimePunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{B}", "{X}{B}{G}", SpellAbilityType.SPLIT);

        // Crime
        // Put target creature or enchantment card from an opponent's graveyard onto the battlefield under your control.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInOpponentsGraveyard(filter));

        // Punishment
        // Destroy each artifact, creature, and enchantment with converted mana cost X.
        this.getRightHalfCard().getSpellAbility().addEffect(new PunishmentEffect());

    }

    public CrimePunishment(final CrimePunishment card) {
        super(card);
    }

    @Override
    public CrimePunishment copy() {
        return new CrimePunishment(this);
    }
}

class PunishmentEffect extends OneShotEffect {

    PunishmentEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy each artifact, creature, and enchantment with converted mana cost X";
    }

    PunishmentEffect(final PunishmentEffect effect) {
        super(effect);
    }

    @Override
    public PunishmentEffect copy() {
        return new PunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent != null
                    && permanent.getConvertedManaCost() == source.getManaCostsToPay().getX()
                    && (permanent.isArtifact()
                    || permanent.isCreature()
                    || permanent.isEnchantment())) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
