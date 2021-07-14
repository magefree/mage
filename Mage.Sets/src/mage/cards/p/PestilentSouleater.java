
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.PhyrexianManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class PestilentSouleater extends CardImpl {

    public PestilentSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(InfectAbility.getInstance(), Duration.EndOfTurn),
                new PhyrexianManaCost(ColoredManaSymbol.B)));
    }

    private PestilentSouleater(final PestilentSouleater card) {
        super(card);
    }

    @Override
    public PestilentSouleater copy() {
        return new PestilentSouleater(this);
    }
}
