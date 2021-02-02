
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class HitchclawRecluse extends CardImpl {

    public HitchclawRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private HitchclawRecluse(final HitchclawRecluse card) {
        super(card);
    }

    @Override
    public HitchclawRecluse copy() {
        return new HitchclawRecluse(this);
    }
}
