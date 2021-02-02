
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class Vorapede extends CardImpl {

    public Vorapede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private Vorapede(final Vorapede card) {
        super(card);
    }

    @Override
    public Vorapede copy() {
        return new Vorapede(this);
    }
}
