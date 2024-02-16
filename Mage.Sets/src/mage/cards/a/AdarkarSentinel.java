
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Sir-Speshkitty
 */
public final class AdarkarSentinel extends CardImpl {

    public AdarkarSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}: Adarkar Sentinel gets +0/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0,1,Duration.EndOfTurn), new ManaCostsImpl<>("{1}"));
        this.addAbility(ability);
    }

    private AdarkarSentinel(final AdarkarSentinel card) {
        super(card);
    }

    @Override
    public AdarkarSentinel copy() {
        return new AdarkarSentinel(this);
    }
}
