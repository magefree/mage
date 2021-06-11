
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward
 */
public final class Spinebiter extends CardImpl {

    public Spinebiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(InfectAbility.getInstance());

        // You may have Spinebiter assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    private Spinebiter(final Spinebiter card) {
        super(card);
    }

    @Override
    public Spinebiter copy() {
        return new Spinebiter(this);
    }
}
