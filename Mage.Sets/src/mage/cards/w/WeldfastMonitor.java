
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class WeldfastMonitor extends CardImpl {

    public WeldfastMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {R}: Weldfast Monitor gains menace until end of turn
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(new MenaceAbility(false), Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.R)));

    }

    private WeldfastMonitor(final WeldfastMonitor card) {
        super(card);
    }

    @Override
    public WeldfastMonitor copy() {
        return new WeldfastMonitor(this);
    }
}
