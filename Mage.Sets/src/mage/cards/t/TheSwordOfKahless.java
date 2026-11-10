package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheSwordOfKahless extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Klingon or Warrior creature card");

    static {
        filter.add(Predicates.or(SubType.KLINGON.getPredicate(), SubType.WARRIOR.getPredicate()));
    }

    public TheSwordOfKahless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has "Whenever this creature attacks, look at the top five cards of your library. You may put a Klingon or Warrior creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order."
        Ability gainedAbility = new AttacksTriggeredAbility(
            new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM)
        );
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(gainedAbility, null).concatBy("and"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private TheSwordOfKahless(final TheSwordOfKahless card) {
        super(card);
    }

    @Override
    public TheSwordOfKahless copy() {
        return new TheSwordOfKahless(this);
    }
}
