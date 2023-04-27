
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class NakayaShade extends CardImpl {

    public NakayaShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SHADE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Nakaya Shade gets +1/+1 until end of turn unless any player pays {2}.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DoUnlessAnyPlayerPaysEffect(new BoostSourceEffect(1,1, Duration.EndOfTurn),new GenericManaCost(2)),
                new ManaCostsImpl<>("{B}")));
    }

    private NakayaShade(final NakayaShade card) {
        super(card);
    }

    @Override
    public NakayaShade copy() {
        return new NakayaShade(this);
    }
}
