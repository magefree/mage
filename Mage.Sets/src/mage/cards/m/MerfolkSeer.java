
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class MerfolkSeer extends CardImpl {

    public MerfolkSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Merfolk Seer dies, you may pay {1}{U}. If you do, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{U}"))));
    }

    private MerfolkSeer(final MerfolkSeer card) {
        super(card);
    }

    @Override
    public MerfolkSeer copy() {
        return new MerfolkSeer(this);
    }
}
