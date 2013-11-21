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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class EtherswornShieldmage extends CardImpl<EtherswornShieldmage> {

    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public EtherswornShieldmage(UUID ownerId) {
        super(ownerId, 4, "Ethersworn Shieldmage", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Vedalken");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ethersworn Shieldmage enters the battlefield, prevent all damage that would be dealt to artifact creatures this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PreventAllDamageToCreatureEffect(Duration.EndOfTurn, filter), false));
    }

    public EtherswornShieldmage(final EtherswornShieldmage card) {
        super(card);
    }

    @Override
    public EtherswornShieldmage copy() {
        return new EtherswornShieldmage(this);
    }
}

class PreventAllDamageToCreatureEffect extends PreventionEffectImpl<PreventAllDamageToCreatureEffect> {

    protected FilterCreaturePermanent filter;

    public PreventAllDamageToCreatureEffect(Duration duration, FilterCreaturePermanent filter) {
        super(duration);
        this.filter = filter;
        staticText = "Prevent all damage that would be dealt to " + filter.getMessage() + " " + duration.toString();
    }

    public PreventAllDamageToCreatureEffect(final PreventAllDamageToCreatureEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public PreventAllDamageToCreatureEffect copy() {
        return new PreventAllDamageToCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
