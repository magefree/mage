
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class FireFieldOgre extends CardImpl {

    public FireFieldOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{U}{B}{R}")));
    }

    private FireFieldOgre(final FireFieldOgre card) {
        super(card);
    }

    @Override
    public FireFieldOgre copy() {
        return new FireFieldOgre(this);
    }
}
