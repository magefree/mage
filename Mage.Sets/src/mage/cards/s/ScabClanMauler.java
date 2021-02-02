
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ScabClanMauler extends CardImpl {

    public ScabClanMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BloodthirstAbility(2));
        this.addAbility(TrampleAbility.getInstance());
    }

    private ScabClanMauler(final ScabClanMauler card) {
        super(card);
    }

    @Override
    public ScabClanMauler copy() {
        return new ScabClanMauler(this);
    }
}
