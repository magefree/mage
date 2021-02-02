
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;

/**
 *
 * @author Loki
 */
public final class KamiOfTheTendedGarden extends CardImpl {

    public KamiOfTheTendedGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, sacrifice Kami of the Tended Garden unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ColoredManaCost(ColoredManaSymbol.G)), TargetController.YOU, false));
        this.addAbility(new SoulshiftAbility(3));
    }

    private KamiOfTheTendedGarden(final KamiOfTheTendedGarden card) {
        super(card);
    }

    @Override
    public KamiOfTheTendedGarden copy() {
        return new KamiOfTheTendedGarden(this);
    }
}
