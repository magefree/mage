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
package mage.sets.battleforzendikar;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class MarchFromTheTomb extends CardImpl {

    public MarchFromTheTomb(UUID ownerId) {
        super(ownerId, 214, "March from the Tomb", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{W}{B}");
        this.expansionSetCode = "BFZ";

        // Return any number of target Ally creature cards with total converted mana cost of 8 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return any number of target Ally creature cards with total converted mana cost of 8 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        FilterCard filter = new FilterCreatureCard();
        filter.add(new SubtypePredicate("Ally"));
        this.getSpellAbility().addTarget(new MarchFromTheTombTarget(0, Integer.MAX_VALUE, filter));
    }

    public MarchFromTheTomb(final MarchFromTheTomb card) {
        super(card);
    }

    @Override
    public MarchFromTheTomb copy() {
        return new MarchFromTheTomb(this);
    }
}

class MarchFromTheTombTarget extends TargetCardInYourGraveyard {

    public MarchFromTheTombTarget(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    public MarchFromTheTombTarget(MarchFromTheTombTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        int cmcLeft = 8;
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                cmcLeft -= card.getManaCost().convertedManaCost();
            }
        }
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> leftPossibleTargets = new HashSet<>();
        for (UUID targetId : possibleTargets) {
            Card card = game.getCard(targetId);
            if (card != null && card.getManaCost().convertedManaCost() <= cmcLeft) {
                leftPossibleTargets.add(targetId);
            }
        }
        setTargetName("any number of target Ally creature cards with total converted mana cost of 8 or less (" + cmcLeft + " left) from your graveyard");
        return leftPossibleTargets;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        if (super.canTarget(playerId, objectId, source, game)) {
            int cmcLeft = 8;
            for (UUID targetId : this.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    cmcLeft -= card.getManaCost().convertedManaCost();
                }
            }
            Card card = game.getCard(objectId);
            return card != null && card.getManaCost().convertedManaCost() <= cmcLeft;
        }
        return false;
    }

    @Override
    public MarchFromTheTombTarget copy() {
        return new MarchFromTheTombTarget(this);
    }

}
