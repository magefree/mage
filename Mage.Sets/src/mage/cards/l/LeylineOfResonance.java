package mage.cards.l;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfResonance extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell(
            "an instant or sorcery spell that targets only a single creature you control"
    );

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public LeylineOfResonance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // If Leyline of Resonance is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Whenever you cast an instant or sorcery spell that targets only a single creature you control, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetStackObjectEffect(), filter,
                false, SetTargetPointer.SPELL
        ));
    }

    private LeylineOfResonance(final LeylineOfResonance card) {
        super(card);
    }

    @Override
    public LeylineOfResonance copy() {
        return new LeylineOfResonance(this);
    }
}
