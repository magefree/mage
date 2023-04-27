
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class DirtwaterWraith extends CardImpl {

    public DirtwaterWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.WRAITH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());

        // {B}: Dirtwater Wraith gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private DirtwaterWraith(final DirtwaterWraith card) {
        super(card);
    }

    @Override
    public DirtwaterWraith copy() {
        return new DirtwaterWraith(this);
    }
}
