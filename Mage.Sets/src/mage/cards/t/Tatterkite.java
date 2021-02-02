
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantHaveCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class Tatterkite extends CardImpl {

    public Tatterkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tatterkite can't have counters put on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantHaveCountersSourceEffect()));

    }

    private Tatterkite(final Tatterkite card) {
        super(card);
    }

    @Override
    public Tatterkite copy() {
        return new Tatterkite(this);
    }
}
