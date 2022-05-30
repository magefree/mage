package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisplacerKitten extends CardImpl {

    public DisplacerKitten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Avoidance — Whenever you cast a noncreature spell, exile up to one target nonland permanent you control, then return that card to the battlefield under its owner's control.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new ExileTargetForSourceEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false));
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND
        ));
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
