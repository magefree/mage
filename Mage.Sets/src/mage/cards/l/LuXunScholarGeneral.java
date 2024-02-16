
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class LuXunScholarGeneral extends CardImpl {

    public LuXunScholarGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Whenever Lu Xun, Scholar General deals damage to an opponent, you may draw a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1), true));

    }

    private LuXunScholarGeneral(final LuXunScholarGeneral card) {
        super(card);
    }

    @Override
    public LuXunScholarGeneral copy() {
        return new LuXunScholarGeneral(this);
    }
}
