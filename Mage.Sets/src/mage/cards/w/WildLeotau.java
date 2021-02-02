
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
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
public final class WildLeotau extends CardImpl {

    public WildLeotau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, sacrifice Wild Leotau unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ColoredManaCost(ColoredManaSymbol.G)), TargetController.YOU, false));
    }

    private WildLeotau(final WildLeotau card) {
        super(card);
    }

    @Override
    public WildLeotau copy() {
        return new WildLeotau(this);
    }
}
