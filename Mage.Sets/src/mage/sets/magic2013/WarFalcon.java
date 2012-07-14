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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class WarFalcon extends CardImpl<WarFalcon> {

    public WarFalcon(UUID ownerId) {
        super(ownerId, 38, "War Falcon", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "M13";
        this.subtype.add("Bird");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // War Falcon can't attack unless you control a Knight or a Soldier.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new WarFalconEffect()));
    }

    public WarFalcon(final WarFalcon card) {
        super(card);
    }

    @Override
    public WarFalcon copy() {
        return new WarFalcon(this);
    }
}

class WarFalconEffect extends RestrictionEffect<WarFalconEffect> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Knight or a Soldier");
    
    static {
        filter.add(Predicates.or(
        new SubtypePredicate("Knight"),
        new SubtypePredicate("Soldier")));
    }

    public WarFalconEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control a Knight or a Soldier";
    }

    public WarFalconEffect(final WarFalconEffect effect) {
        super(effect);
    }

    @Override
    public WarFalconEffect copy() {
        return new WarFalconEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
                return false;
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}

