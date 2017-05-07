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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public class AetherBurst extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Aether Burst");

    static {
        filter.add(new NamePredicate("Aether Burst"));
    }

    public AetherBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast Aether Burst.
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
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        amount += player.getGraveyard().getCards(filter, game).size();
                    }
                }
            }
            ((DynamicTargetCreaturePermanent) target).setMaxNumberOfTargets(amount + 1);
        }
    }

    public AetherBurst(final AetherBurst card) {
        super(card);
    }

    @Override
    public AetherBurst copy() {
        return new AetherBurst(this);
    }
}

class DynamicTargetCreaturePermanent extends TargetPermanent {

    public DynamicTargetCreaturePermanent() {
        super(FILTER_PERMANENT_CREATURES);
    }

    public DynamicTargetCreaturePermanent(final DynamicTargetCreaturePermanent target) {
        super(target);
    }

    @Override
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
        return "Return up to X target creatures to their owners' hands, where X is one plus the number of cards named Aether Burst in all graveyards as you cast Aether Burst";
    }

}
