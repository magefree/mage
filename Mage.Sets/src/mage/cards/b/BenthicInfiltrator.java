
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BenthicInfiltrator extends CardImpl {

    public BenthicInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELDRAZI, SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Ingest
        this.addAbility(new IngestAbility());

        // Benthic Infiltrator can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private BenthicInfiltrator(final BenthicInfiltrator card) {
        super(card);
    }

    @Override
    public BenthicInfiltrator copy() {
        return new BenthicInfiltrator(this);
    }
}
