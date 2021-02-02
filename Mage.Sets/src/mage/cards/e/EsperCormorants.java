

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class EsperCormorants extends CardImpl {

    public EsperCormorants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{W}{U}");
        this.subtype.add(SubType.BIRD);


        this.power = new MageInt(3);
    this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    private EsperCormorants(final EsperCormorants card) {
        super(card);
    }

    @Override
    public EsperCormorants copy() {
        return new EsperCormorants(this);
    }

}
