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
package mage.sets.starwars;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public class Terentatek extends CardImpl {

    public Terentatek(UUID ownerId) {
        super(ownerId, 125, "Terentatek", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "SWS";
        this.subtype.add("Beast");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Terentatek can't attack or block unless you control a Sith.
        // <i>Hate</i> &mdash; If an opponent lost life from source other than combat damage this turn, Terentatek may attack as though you controlled a Sith.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new Terentatek1RestrictionEffect()));

    }

    public Terentatek(final Terentatek card) {
        super(card);
    }

    @Override
    public Terentatek copy() {
        return new Terentatek(this);
    }
}

class Terentatek1RestrictionEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Sith");

    static {
        filter.add(new SubtypePredicate("Sith"));
    }

    public Terentatek1RestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless you control a Sith."
                + "<br><br><i>Hate</i> &mdash; If an opponent lost life from source other than combat damage this turn, {this} may attack as though you controlled a Sith";
    }

    public Terentatek1RestrictionEffect(final Terentatek1RestrictionEffect effect) {
        super(effect);
    }

    @Override
    public Terentatek1RestrictionEffect copy() {
        return new Terentatek1RestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        Ability source = (Ability) getValue("source");
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0
                || HateCondition.getInstance().apply(game, source);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) > 0;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {

        if (permanent.getId().equals(source.getSourceId())) {
            setValue("source", source);
            return true;
        }
        return false;
    }
}
