
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class RangerEnVec extends CardImpl {

    public RangerEnVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.RANGER);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.G)));
    }

    private RangerEnVec(final RangerEnVec card) {
        super(card);
    }

    @Override
    public RangerEnVec copy() {
        return new RangerEnVec(this);
    }
}
