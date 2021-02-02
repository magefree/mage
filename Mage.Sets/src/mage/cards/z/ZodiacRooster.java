
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PlainswalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZodiacRooster extends CardImpl {

    public ZodiacRooster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Plainswalk
        this.addAbility(new PlainswalkAbility());
    }

    private ZodiacRooster(final ZodiacRooster card) {
        super(card);
    }

    @Override
    public ZodiacRooster copy() {
        return new ZodiacRooster(this);
    }
}
