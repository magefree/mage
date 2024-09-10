
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class DeeptreadMerrow extends CardImpl {

    public DeeptreadMerrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(
                new IslandwalkAbility(), Duration.EndOfTurn)
                .setText("{this} gains islandwalk until end of turn"),
                new ColoredManaCost(ColoredManaSymbol.U)));
    }

    private DeeptreadMerrow(final DeeptreadMerrow card) {
        super(card);
    }

    @Override
    public DeeptreadMerrow copy() {
        return new DeeptreadMerrow(this);
    }
}
