

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com, Loki
 */
public final class BirdsOfParadise extends CardImpl {

    public BirdsOfParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new AnyColorManaAbility());
    }

    private BirdsOfParadise(final BirdsOfParadise card) {
        super(card);
    }

    @Override
    public BirdsOfParadise copy() {
        return new BirdsOfParadise(this);
    }

}
