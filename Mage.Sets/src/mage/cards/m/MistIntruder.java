
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class MistIntruder extends CardImpl {

    public MistIntruder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Ingest
        this.addAbility(new IngestAbility());
    }

    private MistIntruder(final MistIntruder card) {
        super(card);
    }

    @Override
    public MistIntruder copy() {
        return new MistIntruder(this);
    }
}
