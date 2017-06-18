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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public class MaraudingBoneslasher extends CardImpl {

    public MaraudingBoneslasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add("Zombie");
        this.subtype.add("Zombie");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Marauding Boneslasher can't block unless you control another Zombie.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaraudingBoneslasherRestrictionEffect()));
    }

    public MaraudingBoneslasher(final MaraudingBoneslasher card) {
        super(card);
    }

    @Override
    public MaraudingBoneslasher copy() {
        return new MaraudingBoneslasher(this);
    }
}

class MaraudingBoneslasherRestrictionEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Zombie");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
        filter.add(new AnotherPredicate());
    }

    public MaraudingBoneslasherRestrictionEffect() {
        super(Duration.WhileOnBattlefield);

        staticText = "{this} can't block unless you control another Zombie";
    }

    public MaraudingBoneslasherRestrictionEffect(final MaraudingBoneslasherRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public MaraudingBoneslasherRestrictionEffect copy() {
        return new MaraudingBoneslasherRestrictionEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())
                && game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) == 0) {
            return true;
        }
        return false;
    }
}
