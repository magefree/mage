
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author Loki
 */
public final class WarSpikeChangeling extends CardImpl {

    public WarSpikeChangeling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new ChangelingAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
    }

    private WarSpikeChangeling(final WarSpikeChangeling card) {
        super(card);
    }

    @Override
    public WarSpikeChangeling copy() {
        return new WarSpikeChangeling(this);
    }
}
