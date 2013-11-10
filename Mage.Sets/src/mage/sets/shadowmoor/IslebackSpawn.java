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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import static mage.abilities.condition.Condition.ComparisonType.Equal;
import static mage.abilities.condition.Condition.ComparisonType.GreaterThan;
import static mage.abilities.condition.Condition.ComparisonType.LessThan;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class IslebackSpawn extends CardImpl<IslebackSpawn> {

    public IslebackSpawn(UUID ownerId) {
        super(ownerId, 40, "Isleback Spawn", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Kraken");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(8);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // Isleback Spawn gets +4/+8 as long as a library has twenty or fewer cards in it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConditionalContinousEffect(
                new BoostSourceEffect(4,8, Duration.EndOfGame),
                new CardsInAnyLibraryCondition(Condition.ComparisonType.LessThan, 21),
                "{this} gets +4/+8 as long as a library has twenty or fewer cards in it")));
    }

    public IslebackSpawn(final IslebackSpawn card) {
        super(card);
    }

    @Override
    public IslebackSpawn copy() {
        return new IslebackSpawn(this);
    }
}

class CardsInAnyLibraryCondition implements Condition {

    protected final Condition.ComparisonType type;
    protected final int value;

    public CardsInAnyLibraryCondition(Condition.ComparisonType type, int value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public final boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    switch(type) {
                        case GreaterThan:
                            if (player.getLibrary().size() > value) {
                                return true;
                            }
                            break;
                        case Equal:
                            if (player.getLibrary().size() == value) {
                                return true;
                            }
                            break;
                        case LessThan:
                            if (player.getLibrary().size() < value) {
                                return true;
                            }
                            break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("a library has ");
        switch(type) {
            case GreaterThan:
                sb.append(value+1).append(" or more cards in it ");
                break;
            case Equal:
                sb.append(value).append(" cards in it ");
                break;
            case LessThan:
                sb.append(value-1).append(" or fewer cards in it ");
                break;
        }
        return sb.toString();
    }
}
