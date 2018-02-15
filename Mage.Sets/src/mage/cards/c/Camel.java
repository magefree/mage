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
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.DamageCreatureEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class Camel extends CardImpl {

    public Camel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // As long as Camel is attacking, prevent all damage Deserts would deal to Camel and to creatures banded with Camel.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CamelEffect()));

    }

    public Camel(final Camel card) {
        super(card);
    }

    @Override
    public Camel copy() {
        return new Camel(this);
    }
}

class CamelEffect extends PreventionEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent("Deserts");

    static {
        filter.add(new SubtypePredicate(SubType.DESERT));
    }

    CamelEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "As long as {this} is attacking, prevent all damage Deserts would deal to {this} and to creatures banded with {this}";
    }

    CamelEffect(final CamelEffect effect) {
        super(effect);
    }

    @Override
    public CamelEffect copy() {
        return new CamelEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageCreatureEvent && event.getAmount() > 0) {
            DamageCreatureEvent damageEvent = (DamageCreatureEvent) event;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null 
                    && sourcePermanent.isAttacking()
                    && (event.getTargetId().equals(source.getSourceId()) || sourcePermanent.getBandedCards().contains(event.getTargetId()))) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
