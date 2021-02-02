
package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.MeldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Rarity;
import mage.constants.TargetController;

/**
 * @author emerald000
 */
public final class GrafRats extends CardImpl {

    public GrafRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, if you both own and control Graf Rats and a creature named Midnight Scavengers, exile them, then meld them into Chittering Host.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new MeldEffect("Midnight Scavengers", new mage.cards.c.ChitteringHost(ownerId, new CardSetInfo("Chittering Host", "EMN", "96", Rarity.COMMON))), TargetController.YOU, false),
                new MeldCondition("Midnight Scavengers"),
                "At the beginning of combat on your turn, if you both own and control {this} and a creature named Midnight Scavengers, exile them, then meld them into Chittering Host."));
    }

    private GrafRats(final GrafRats card) {
        super(card);
    }

    @Override
    public GrafRats copy() {
        return new GrafRats(this);
    }
}