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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class OracleOfBones extends CardImpl<OracleOfBones> {

    public OracleOfBones(UUID ownerId) {
        super(ownerId, 103, "Oracle of Bones", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Minotaur");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Tribute 2
        this.addAbility(new TributeAbility(2));
        // When Oracle of Bones enters the battlefield, if tribute wasn't paid, you may cast an instant or sorcery card from your hand without paying its mana cost.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new OracleOfBonesCastEffect(), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, TributeNotPaidCondition.getInstance(),
                "When {this} enters the battlefield, if its tribute wasn't paid, you may cast an instant or sorcery card from your hand without paying its mana cost."));
    }

    public OracleOfBones(final OracleOfBones card) {
        super(card);
    }

    @Override
    public OracleOfBones copy() {
        return new OracleOfBones(this);
    }
}

class OracleOfBonesCastEffect extends OneShotEffect<OracleOfBonesCastEffect> {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from your hand");

    public OracleOfBonesCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "you may cast an instant or sorcery card from your hand without paying its mana cost";
    }

    public OracleOfBonesCastEffect(final OracleOfBonesCastEffect effect) {
        super(effect);
    }

    @Override
    public OracleOfBonesCastEffect copy() {
        return new OracleOfBonesCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetCardInHand(filter);
            target.setRequired(true);
            if (target.canChoose(source.getSourceId(), controller.getId(), game) &&
              controller.chooseUse(outcome, "Cast an instant or sorcery card from your hand without paying its mana cost?", game)) {
                Card cardToCast = null;
                boolean cancel = false;
                while (!cancel) {
                    if (controller.chooseTarget(outcome, target, source, game)) {
                        cardToCast = game.getCard(target.getFirstTarget());
                        if (cardToCast != null && cardToCast.getSpellAbility().canChooseTarget(game)) {
                            cancel = true;
                        }
                    } else {
                        cancel = true;
                    }
                }
                if (cardToCast != null) {
                    controller.cast(cardToCast.getSpellAbility(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}
