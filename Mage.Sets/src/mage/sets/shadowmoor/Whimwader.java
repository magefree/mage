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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth

 */
public class Whimwader extends CardImpl<Whimwader> {

    public Whimwader(UUID ownerId) {
        super(ownerId, 54, "Whimwader", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");

        this.color.setBlue(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whimwader can't attack unless defending player controls a blue permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WhimwaderCantAttackEffect()));
        
    }

    public Whimwader(final Whimwader card) {
        super(card);
    }

    @Override
    public Whimwader copy() {
        return new Whimwader(this);
    }
}

class WhimwaderCantAttackEffect extends ReplacementEffectImpl<WhimwaderCantAttackEffect> {

    private static final FilterPermanent filter = new FilterPermanent();;
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public WhimwaderCantAttackEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "{this} can't attack unless defending player controls a blue permanent";
    }

    public WhimwaderCantAttackEffect(final WhimwaderCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public WhimwaderCantAttackEffect copy() {
        return new WhimwaderCantAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER 
                && source.getSourceId().equals(event.getSourceId())) {
            if (game.getBattlefield().countAll(filter, event.getTargetId(), game) == 0) {
                return true;
            }
        }
        return false;
    }
}