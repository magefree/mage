package mage.cards.r;

import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RobeOfTheArchmagi extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Shaman, Warlock, or Wizard");

    static {
        filter.add(Predicates.or(
                SubType.SHAMAN.getPredicate(),
                SubType.WARLOCK.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    public RobeOfTheArchmagi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage to a player, you draw that many cards.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY)
                        .setText("you draw that many cards"),
                "equipped", false
        ));

        // Equip {4}
        this.addAbility(new EquipAbility(4, false));

        // Equip Shaman, Warlock, or Wizard {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1), new TargetPermanent(filter), false));
    }

    private RobeOfTheArchmagi(final RobeOfTheArchmagi card) {
        super(card);
    }

    @Override
    public RobeOfTheArchmagi copy() {
        return new RobeOfTheArchmagi(this);
    }
}
