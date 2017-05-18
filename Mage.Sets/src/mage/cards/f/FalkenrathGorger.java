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
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class FalkenrathGorger extends CardImpl {

    public FalkenrathGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add("Vampire");
        this.subtype.add("Berserker");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        /**
         * 4/8/2016 Falkenrath Gorger’s ability only applies while it’s on the
         * battlefield. If you discard it, it won’t give itself madness.
         * 4/8/2016 If Falkenrath Gorger leaves the battlefield before the
         * madness trigger has resolved for a Vampire card that gained madness
         * with its ability, the madness ability will still let you cast that
         * Vampire card for the appropriate cost even though it no longer has
         * madness. 4/8/2016 If you discard a Vampire creature card that already
         * has a madness ability, you’ll choose which madness ability exiles it.
         * You may choose either the one it normally has or the one it gains
         * from Falkenrath Gorger.
         */
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
        filter.add(new SubtypePredicate(SubType.VAMPIRE));

    }

    HashMap<UUID, MadnessAbility> madnessAbilities = new HashMap<>(); // reuse the same ability for the same object

    public FalkenrathGorgerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost";
    }

    public FalkenrathGorgerEffect(final FalkenrathGorgerEffect effect) {
        super(effect);
        this.madnessAbilities.putAll(effect.madnessAbilities);
    }

    @Override
    public FalkenrathGorgerEffect copy() {
        return new FalkenrathGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            HashMap<UUID, MadnessAbility> usedMadnessAbilities = new HashMap<>();
            // hand
            for (Card card : controller.getHand().getCards(filter, game)) {
                addMadnessToCard(game, card, usedMadnessAbilities);
            }
            // graveyard
            for (Card card : controller.getGraveyard().getCards(filter, game)) {
                addMadnessToCard(game, card, usedMadnessAbilities);
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                if (filter.match(card, source.getSourceId(), controller.getId(), game)) {
                    if (card.getOwnerId().equals(controller.getId())) {
                        addMadnessToCard(game, card, usedMadnessAbilities);
                    }
                }
            }
            madnessAbilities.clear();
            madnessAbilities.putAll(usedMadnessAbilities);
            return true;
        }

        return false;
    }

    private void addMadnessToCard(Game game, Card card, HashMap<UUID, MadnessAbility> usedMadnessAbilities) {
        MadnessAbility ability = madnessAbilities.get(card.getId());
        if (ability == null) {
            ability = new MadnessAbility(card, card.getSpellAbility().getManaCosts());
        }
        game.getState().addOtherAbility(card, ability, false);
        usedMadnessAbilities.put(card.getId(), ability);
    }
}
