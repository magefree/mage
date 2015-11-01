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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BursegSardaukar

 */
public class MoggJailer extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature with power 2 or less");

    static {
        filter.add(Predicates.and(new PowerPredicate(Filter.ComparisonType.LessThan, 2), Predicates.not(new TappedPredicate())));
        //filter.add(new PowerPredicate(Filter.ComparisonType.LessThan, 3));
    }
    
    public MoggJailer(UUID ownerId) {
        super(ownerId, 68, "Mogg Jailer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Goblin");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mogg Jailer can't attack if defending player controls an untapped creature with power 2 or less.
        Effect effect = new CantAttackIfDefenderControllsPermanent(filter);
        effect.setText("Mogg Jailer can't attack if defending player controls an untapped creature with power 2 or less.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public MoggJailer(final MoggJailer card) {
        super(card);
    }

    @Override
    public MoggJailer copy() {
        return new MoggJailer(this);
    }
}

class CantAttackIfDefenderControllsPermanent extends RestrictionEffect {

    private final FilterPermanent filter;

    public CantAttackIfDefenderControllsPermanent(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't attack if defending player controls ").append(filter.getMessage()).toString();
    }

    public CantAttackIfDefenderControllsPermanent(final CantAttackIfDefenderControllsPermanent effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(UUID defenderId, Ability source, Game game) {
        UUID defendingPlayerId;
        Player player = game.getPlayer(defenderId);
        if (player == null) {
            Permanent permanent = game.getPermanent(defenderId);
            if (permanent != null) {
                defendingPlayerId = permanent.getControllerId();
            } else {
                return true;
            }
        } else {
            defendingPlayerId = defenderId;
        }
        if (defendingPlayerId != null && game.getBattlefield().countAll(filter, defendingPlayerId, game) > 0) {
            return false;
        }
        return true;
    }

    @Override
    public CantAttackIfDefenderControllsPermanent copy() {
        return new CantAttackIfDefenderControllsPermanent(this);
    }

}