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
package mage.sets.legends;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HazezonTamar extends CardImpl<HazezonTamar> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sand Warriors");

    static {
        filter.add(new SubtypePredicate("Sand"));
        filter.add(new SubtypePredicate("Warrior"));
    }

    public HazezonTamar(UUID ownerId) {
        super(ownerId, 270, "Hazezon Tamar", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{G}{W}");
        this.expansionSetCode = "LEG";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Hazezon Tamar enters the battlefield, put X 1/1 Sand Warrior creature tokens that are red, green, and white onto the battlefield at the beginning of your next upkeep, where X is the number of lands you control at that time.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HazezonTamarEntersEffect(), false));
        // When Hazezon leaves the battlefield, exile all Sand Warriors.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filter), false));
    }

    public HazezonTamar(final HazezonTamar card) {
        super(card);
    }

    @Override
    public HazezonTamar copy() {
        return new HazezonTamar(this);
    }
}

class HazezonTamarEntersEffect extends OneShotEffect<HazezonTamarEntersEffect> {

    public HazezonTamarEntersEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put X 1/1 Sand Warrior creature tokens that are red, green, and white onto the battlefield at the beginning of your next upkeep, where X is the number of lands you control at that time";
    }

    public HazezonTamarEntersEffect(final HazezonTamarEntersEffect effect) {
        super(effect);
    }

    @Override
    public HazezonTamarEntersEffect copy() {
        return new HazezonTamarEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Effect effect = new CreateTokenEffect(new HazezonTamarSandWarrior(), new PermanentsOnBattlefieldCount(new FilterControlledLandPermanent()));
            effect.setText("put X 1/1 Sand Warrior creature tokens that are red, green, and white onto the battlefield, where X is the number of lands you control at that time");
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(effect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}

class HazezonTamarSandWarrior extends Token {

    public HazezonTamarSandWarrior() {
        super("Sand Warrior", "1/1 Sand Warrior creature tokens that are red, green, and white");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add("Sand");
        subtype.add("Warrior");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

}
