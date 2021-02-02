
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
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
public final class HorrorOfTheDim extends CardImpl {

    public HorrorOfTheDim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);


        // {U}: Horror of the Dim gains hexproof until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn),
                new ColoredManaCost(ColoredManaSymbol.U)));
    }

    private HorrorOfTheDim(final HorrorOfTheDim card) {
        super(card);
    }

    @Override
    public HorrorOfTheDim copy() {
        return new HorrorOfTheDim(this);
    }
}
