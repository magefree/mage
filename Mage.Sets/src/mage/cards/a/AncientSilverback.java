
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class AncientSilverback extends CardImpl {

    public AncientSilverback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")));
    }

    private AncientSilverback(final AncientSilverback card) {
        super(card);
    }

    @Override
    public AncientSilverback copy() {
        return new AncientSilverback(this);
    }
}
