
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Sir-Speshkitty
 */
public final class AccursedCentaur extends CardImpl {

    public AccursedCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Accursed Centaur enters the battlefield, sacrifice a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, null)));
    }

    private AccursedCentaur(final AccursedCentaur card) {
        super(card);
    }

    @Override
    public AccursedCentaur copy() {
        return new AccursedCentaur(this);
    }
}
