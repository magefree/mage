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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerControlsIslandPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author KholdFuzion

 */
public class SeaSerpent extends CardImpl<SeaSerpent> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("an Island");

    static {
        filter.add(new SubtypePredicate("Island"));
    }

    public SeaSerpent(UUID ownerId) {
        super(ownerId, 77, "Sea Serpent", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{U}");
        this.expansionSetCode = "LEA";
        this.subtype.add("Serpent");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sea Serpent can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(filter)));
        // When you control no Islands, sacrifice Sea Serpent.
        this.addAbility(new SeaSerpentTriggeredAbility());
    }

    public SeaSerpent(final SeaSerpent card) {
        super(card);
    }

    @Override
    public SeaSerpent copy() {
        return new SeaSerpent(this);
    }
}

class SeaSerpentTriggeredAbility extends StateTriggeredAbility<SeaSerpentTriggeredAbility> {

    public SeaSerpentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public SeaSerpentTriggeredAbility(final SeaSerpentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SeaSerpentTriggeredAbility copy() {
        return new SeaSerpentTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return (game.getBattlefield().countAll(ControllerControlsIslandPredicate.filter, controllerId, game) == 0);
    }

    @Override
    public String getRule() {
        return "When you control no islands, sacrifice {this}.";
    }
}
