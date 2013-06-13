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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class SpireTracer extends CardImpl<SpireTracer> {

    public SpireTracer(UUID ownerId) {
        super(ownerId, 135, "Spire Tracer", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Elf");
        this.subtype.add("Scout");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spire Tracer can't be blocked except by creatures with flying or reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect()));

    }

    public SpireTracer(final SpireTracer card) {
        super(card);
    }

    @Override
    public SpireTracer copy() {
        return new SpireTracer(this);
    }
}

class CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect extends RestrictionEffect<CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect> {

    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "Can't be blocked except by creatures with flying or reach";
    }

    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect(final CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker.getAbilities().containsKey(FlyingAbility.getInstance().getId())
                || blocker.getAbilities().containsKey(ReachAbility.getInstance().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect copy() {
        return new CantBeBlockedExceptByCreaturesWithFlyingOrReachEffect(this);
    }
}
