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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author North
 */
public class SerpentOfTheEndlessSea extends CardImpl<SerpentOfTheEndlessSea> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("Islands you control");

    static {
        filter.getSubtype().add("Island");
    }

    public SerpentOfTheEndlessSea(UUID ownerId) {
        super(ownerId, 70, "Serpent of the Endless Sea", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "M10";
        this.subtype.add("Serpent");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Serpent of the Endless Sea's power and toughness are each equal to the number of Islands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));
        // Serpent of the Endless Sea can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SerpentOfTheEndlessSeaEffect()));
    }

    public SerpentOfTheEndlessSea(final SerpentOfTheEndlessSea card) {
        super(card);
    }

    @Override
    public SerpentOfTheEndlessSea copy() {
        return new SerpentOfTheEndlessSea(this);
    }
}

class SerpentOfTheEndlessSeaEffect extends ReplacementEffectImpl<SerpentOfTheEndlessSeaEffect> {

    public SerpentOfTheEndlessSeaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "{this} can't attack unless defending player controls an Island";
    }

    public SerpentOfTheEndlessSeaEffect(final SerpentOfTheEndlessSeaEffect effect) {
        super(effect);
    }

    @Override
    public SerpentOfTheEndlessSeaEffect copy() {
        return new SerpentOfTheEndlessSeaEffect(this);
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
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER && source.getSourceId().equals(event.getSourceId())) {
            FilterPermanent filter = new FilterPermanent();
            filter.getSubtype().add("Island");

            if (game.getBattlefield().countAll(filter, event.getTargetId(), game) == 0) {
                return true;
            }
        }
        return false;
    }
}
