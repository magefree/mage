
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MinotaurAbomination extends CardImpl {

    public MinotaurAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MINOTAUR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);
    }

    private MinotaurAbomination(final MinotaurAbomination card) {
        super(card);
    }

    @Override
    public MinotaurAbomination copy() {
        return new MinotaurAbomination(this);
    }
}
