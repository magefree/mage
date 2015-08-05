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
package mage.sets.morningtide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class DistantMelody extends CardImpl {

    public DistantMelody(UUID ownerId) {
        super(ownerId, 32, "Distant Melody", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{U}");
        this.expansionSetCode = "MOR";


        // Choose a creature type. Draw a card for each permanent you control of that type.
        this.getSpellAbility().addEffect(new DistantMelodyEffect());
    }

    public DistantMelody(final DistantMelody card) {
        super(card);
    }

    @Override
    public DistantMelody copy() {
        return new DistantMelody(this);
    }
}

class DistantMelodyEffect extends OneShotEffect {
    
    DistantMelodyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose a creature type. Draw a card for each permanent you control of that type";
    }
    
    DistantMelodyEffect(final DistantMelodyEffect effect) {
        super(effect);
    }
    
    @Override
    public DistantMelodyEffect copy() {
        return new DistantMelodyEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose a creature type:");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
            filter.add(new SubtypePredicate(typeChoice.getChoice()));
            return new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)).apply(game, source);
        }
        return false;
    }
}
