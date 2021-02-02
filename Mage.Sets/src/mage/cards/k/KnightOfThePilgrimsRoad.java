
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KnightOfThePilgrimsRoad extends CardImpl {

    public KnightOfThePilgrimsRoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Renown 1 (When this creature deals combat damage to a player, if it isn't renowned, put a +1/+1 counter on it and it becomes renowned.)
        this.addAbility(new RenownAbility(1));
    }

    private KnightOfThePilgrimsRoad(final KnightOfThePilgrimsRoad card) {
        super(card);
    }

    @Override
    public KnightOfThePilgrimsRoad copy() {
        return new KnightOfThePilgrimsRoad(this);
    }
}
