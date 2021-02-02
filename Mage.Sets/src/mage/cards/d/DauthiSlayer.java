
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DauthiSlayer extends CardImpl {

    public DauthiSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Dauthi Slayer attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private DauthiSlayer(final DauthiSlayer card) {
        super(card);
    }

    @Override
    public DauthiSlayer copy() {
        return new DauthiSlayer(this);
    }
}
