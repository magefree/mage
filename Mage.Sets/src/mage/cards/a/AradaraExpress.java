
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class AradaraExpress extends CardImpl {

    public AradaraExpress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));
        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private AradaraExpress(final AradaraExpress card) {
        super(card);
    }

    @Override
    public AradaraExpress copy() {
        return new AradaraExpress(this);
    }
}
