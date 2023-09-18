

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class SireOfTheStorm extends CardImpl {

    public SireOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SPIRIT);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private SireOfTheStorm(final SireOfTheStorm card) {
        super(card);
    }

    @Override
    public SireOfTheStorm copy() {
        return new SireOfTheStorm(this);
    }

}
