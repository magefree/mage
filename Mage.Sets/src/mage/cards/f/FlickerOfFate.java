package mage.cards.f;

import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlickerOfFate extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public FlickerOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature or enchantment, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false, "it").concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private FlickerOfFate(final FlickerOfFate card) {
        super(card);
    }

    @Override
    public FlickerOfFate copy() {
        return new FlickerOfFate(this);
    }
}
