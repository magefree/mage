
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class StormboundGeist extends CardImpl {

    public StormboundGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Stormbound Geist can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private StormboundGeist(final StormboundGeist card) {
        super(card);
    }

    @Override
    public StormboundGeist copy() {
        return new StormboundGeist(this);
    }
}
