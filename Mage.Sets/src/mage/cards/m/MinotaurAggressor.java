

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MinotaurAggressor extends CardImpl {

    public MinotaurAggressor (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(6);
        this.toughness = new MageInt(2);

        // First strike, haste
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private MinotaurAggressor(final MinotaurAggressor card) {
        super(card);
    }

    @Override
    public MinotaurAggressor copy() {
        return new MinotaurAggressor(this);
    }
}
