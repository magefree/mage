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
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author emerald000
 */
public class CoordinatedBarrage extends CardImpl {

    public CoordinatedBarrage(UUID ownerId) {
        super(ownerId, 7, "Coordinated Barrage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "MOR";

        // Choose a creature type. Coordinated Barrage deals damage to target attacking or blocking creature equal to the number of permanents you control of the chosen type.
        this.getSpellAbility().addEffect(new CoordinatedBarrageEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    public CoordinatedBarrage(final CoordinatedBarrage card) {
        super(card);
    }

    @Override
    public CoordinatedBarrage copy() {
        return new CoordinatedBarrage(this);
    }
}

class CoordinatedBarrageEffect extends OneShotEffect {

    CoordinatedBarrageEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a creature type. Coordinated Barrage deals damage to target attacking or blocking creature equal to the number of permanents you control of the chosen type";
    }

    CoordinatedBarrageEffect(final CoordinatedBarrageEffect effect) {
        super(effect);
    }

    @Override
    public CoordinatedBarrageEffect copy() {
        return new CoordinatedBarrageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose a creature type");
            choice.setChoices(CardRepository.instance.getCreatureTypes());
            if (controller.choose(Outcome.Damage, choice, game)) {
                String chosenType = choice.getChoice();
                FilterControlledPermanent filter = new FilterControlledPermanent();
                filter.add(new SubtypePredicate(chosenType));
                int damageDealt = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
                Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    permanent.damage(damageDealt, source.getSourceId(), game, false, true);
                }
                return true;
            }
        }
        return false;
    }
}
