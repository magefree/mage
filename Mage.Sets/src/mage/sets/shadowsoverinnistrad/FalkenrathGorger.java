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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class FalkenrathGorger extends CardImpl {

    public FalkenrathGorger(UUID ownerId) {
        super(ownerId, 155, "Falkenrath Gorger", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Vampire");
        this.subtype.add("Warrior");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FalkenrathGorgerEffect()));
    }

    public FalkenrathGorger(final FalkenrathGorger card) {
        super(card);
    }

    @Override
    public FalkenrathGorger copy() {
        return new FalkenrathGorger(this);
    }
}

class FalkenrathGorgerEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Vampire creature card you own");

    static {
        filter.add(new SubtypePredicate("Vampire"));

    }

    public FalkenrathGorgerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost";
    }

    public FalkenrathGorgerEffect(final FalkenrathGorgerEffect effect) {
        super(effect);
    }

    @Override
    public FalkenrathGorgerEffect copy() {
        return new FalkenrathGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // hand
            for (Card card : controller.getHand().getCards(filter, game)) {
                game.getState().addOtherAbility(card, new MadnessAbility(card, card.getSpellAbility().getManaCosts()));
            }
            // graveyard
            for (Card card : controller.getGraveyard().getCards(filter, game)) {
                game.getState().addOtherAbility(card, new MadnessAbility(card, card.getSpellAbility().getManaCosts()));
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                if (filter.match(card, source.getSourceId(), controller.getId(), game)) {
                    if (card.getOwnerId().equals(controller.getId())) {
                        game.getState().addOtherAbility(card, new MadnessAbility(card, card.getSpellAbility().getManaCosts()));
                    }
                }
            }
            return true;
        }

        return false;
    }
}
