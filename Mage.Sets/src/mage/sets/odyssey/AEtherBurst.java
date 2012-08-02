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
package mage.sets.odyssey;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class AEtherBurst extends CardImpl<AEtherBurst> {

    private static FilterCard filter = new FilterCard("cards named AEther Burst");

    static {
        filter.add(new NamePredicate("AEther Burst"));
    }

    public AEtherBurst(UUID ownerId) {
        super(ownerId, 60, "AEther Burst", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ODY";

        this.color.setBlue(true);

        // Return up to X target creatures to their owners' hands, where X is one plus the number of cards named AEther Burst in all graveyards as you cast AEther Burst.
        this.getSpellAbility().addEffect(new DynamicReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new DynamicTargetCreaturePermanent());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Target target = ability.getTargets().get(0);
        if (target instanceof DynamicTargetCreaturePermanent) {
            Player controller = game.getPlayer(ability.getControllerId());
            int amount = 0;
            if (controller != null) {
                for (UUID playerId : controller.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        amount += player.getGraveyard().getCards(filter, game).size();
                    }
                }
            }
            ((DynamicTargetCreaturePermanent)target).setMaxNumberOfTargets(amount + 1);
        }
    }

    public AEtherBurst(final AEtherBurst card) {
        super(card);
    }

    @Override
    public AEtherBurst copy() {
        return new AEtherBurst(this);
    }
}

class DynamicTargetCreaturePermanent<T extends DynamicTargetCreaturePermanent<T>> extends TargetCreaturePermanent<DynamicTargetCreaturePermanent<T>> {

    private static FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creatures");

    public DynamicTargetCreaturePermanent() {
        super(new TargetCreaturePermanent(filterCreature));
    }

    public DynamicTargetCreaturePermanent(final DynamicTargetCreaturePermanent target) {
        super(target);
    }

    public void setMaxNumberOfTargets(int maxNumberOfTargets) {
        this.maxNumberOfTargets = maxNumberOfTargets;
    }

    @Override
    public DynamicTargetCreaturePermanent copy() {
        return new DynamicTargetCreaturePermanent(this);
    }

}

/**
 * We extend ReturnToHandTargetEffect class just to override the rules.
 */
class DynamicReturnToHandTargetEffect extends ReturnToHandTargetEffect {

    public DynamicReturnToHandTargetEffect() {
        super();
    }

    public DynamicReturnToHandTargetEffect(final DynamicReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public DynamicReturnToHandTargetEffect copy() {
        return new DynamicReturnToHandTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Return up to X target creatures to their owners' hands, where X is one plus the number of cards named AEther Burst in all graveyards as you cast AEther Burst";
    }

}

