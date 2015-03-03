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
package mage.sets.fatereforged;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ShiftingLoyalties extends CardImpl {

    public ShiftingLoyalties(UUID ownerId) {
        super(ownerId, 51, "Shifting Loyalties", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{5}{U}");
        this.expansionSetCode = "FRF";

        // Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target permanents that share a card type. <i>(Artifact, creature, enchantment, land, and planeswalker are card types.)</i>"));
        this.getSpellAbility().addTarget(new TargetpermanentsThatShareCardType());

    }

    public ShiftingLoyalties(final ShiftingLoyalties card) {
        super(card);
    }

    @Override
    public ShiftingLoyalties copy() {
        return new ShiftingLoyalties(this);
    }
}

class TargetpermanentsThatShareCardType extends TargetPermanent {

    public TargetpermanentsThatShareCardType() {
        super(2, 2, new FilterPermanent(), false);
        targetName = "permanents that share a card type";
    }

    public TargetpermanentsThatShareCardType(final TargetpermanentsThatShareCardType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            if (!getTargets().isEmpty()) {
                Permanent targetOne = game.getPermanent(getTargets().get(0));
                Permanent targetTwo = game.getPermanent(id);
                if (targetOne == null || targetTwo == null) {
                    return false;
                }
                return CardUtil.shareTypes(targetOne, targetTwo);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<CardType> cardTypes = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
            if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                for (CardType cardType :permanent.getCardType()) {
                    if (cardTypes.contains(cardType)) {
                        return true;
                    }
                }
                cardTypes.addAll(permanent.getCardType());
            }
        }
        return false;
    }

    @Override
    public TargetpermanentsThatShareCardType copy() {
        return new TargetpermanentsThatShareCardType(this);
    }
}
