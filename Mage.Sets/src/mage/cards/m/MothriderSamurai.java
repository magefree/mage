

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class MothriderSamurai extends CardImpl {

    public MothriderSamurai (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new BushidoAbility(1));
    }

    private MothriderSamurai(final MothriderSamurai card) {
        super(card);
    }

    @Override
    public MothriderSamurai copy() {
        return new MothriderSamurai(this);
    }

}
