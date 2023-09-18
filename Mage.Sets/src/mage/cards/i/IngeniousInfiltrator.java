package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IngeniousInfiltrator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.NINJA, "a Ninja you control");

    public IngeniousInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ninjutsu {U}{B}
        this.addAbility(new NinjutsuAbility("{U}{B}"));

        // Whenever a Ninja you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private IngeniousInfiltrator(final IngeniousInfiltrator card) {
        super(card);
    }

    @Override
    public IngeniousInfiltrator copy() {
        return new IngeniousInfiltrator(this);
    }
}
