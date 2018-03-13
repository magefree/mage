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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.DamageCreatureEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class TresserhornSkyknight extends CardImpl {

    public TresserhornSkyknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all damage that would be dealt to Tresserhorn Skyknight by creatures with first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TresserhornSkyknightEffect()));
    }

    public TresserhornSkyknight(final TresserhornSkyknight card) {
        super(card);
    }

    @Override
    public TresserhornSkyknight copy() {
        return new TresserhornSkyknight(this);
    }
}

class TresserhornSkyknightEffect extends PreventionEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with first strike");

    static {
        filter.add(new AbilityPredicate(FirstStrikeAbility.class));
    }

    TresserhornSkyknightEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to Tresserhorn Skyknight by creatures with first strike";
    }

    TresserhornSkyknightEffect(final TresserhornSkyknightEffect effect) {
        super(effect);
    }

    @Override
    public TresserhornSkyknightEffect copy() {
        return new TresserhornSkyknightEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageCreatureEvent && event.getAmount() > 0) {
            DamageCreatureEvent damageEvent = (DamageCreatureEvent) event;
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
