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
package mage.sets.jacevschandra;

import java.util.Arrays;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class Hostility extends CardImpl {

    public Hostility(UUID ownerId) {
        super(ownerId, 48, "Hostility", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}{R}");
        this.expansionSetCode = "DD2";
        this.subtype.add("Elemental");
        this.subtype.add("Incarnation");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If a spell you control would deal damage to an opponent, prevent that damage.
        // Put a 3/1 red Elemental Shaman creature token with haste onto the battlefield for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HostilityEffect()));

        // When Hostility is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    public Hostility(final Hostility card) {
        super(card);
    }

    @Override
    public Hostility copy() {
        return new Hostility(this);
    }
}

class HostilityEffect extends PreventionEffectImpl {

    public HostilityEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If a spell you control would deal damage to an opponent, prevent that damage. Put a 3/1 red Elemental Shaman creature token with haste onto the battlefield for each 1 damage prevented this way.";
    }

    public HostilityEffect(final HostilityEffect effect) {
        super(effect);
    }

    @Override
    public HostilityEffect copy() {
        return new HostilityEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (game.getOpponents(source.getControllerId()).contains(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        if (preventionEffectData.getPreventedDamage() > 0) {
            new CreateTokenEffect(new HostilityElementalToken(), preventionEffectData.getPreventedDamage()).apply(game, source);
        }
        return true;
    }
}

class HostilityElementalToken extends Token {

    public HostilityElementalToken() {
        super("Elemental Shaman", "3/1 red Elemental Shaman creature token with haste");
        availableImageSetCodes.addAll(Arrays.asList("LRW", "DD2"));
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Elemental");
        subtype.add("Shaman");
        power = new MageInt(3);
        toughness = new MageInt(1);

        addAbility(HasteAbility.getInstance());
    }

}
