

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class VectorAsp extends CardImpl {

    public VectorAsp (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(InfectAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    public VectorAsp (final VectorAsp card) {
        super(card);
    }

    @Override
    public VectorAsp copy() {
        return new VectorAsp(this);
    }

}
