package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisplacerKitten extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DisplacerKitten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Avoidance — Whenever you cast a noncreature spell, exile up to one target nonland permanent you control, then return that card to the battlefield under its owner's control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ExileThenReturnTargetEffect(false, true), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability.withFlavorWord("Avoidance"));
    }

    private DisplacerKitten(final DisplacerKitten card) {
        super(card);
    }

    @Override
    public DisplacerKitten copy() {
        return new DisplacerKitten(this);
    }
}
