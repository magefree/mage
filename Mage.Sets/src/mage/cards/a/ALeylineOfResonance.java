package mage.cards.a;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
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
 * @author LevelX2
 */
public final class ALeylineOfResonance extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell(
            "an instant or sorcery spell that targets only a single creature you control"
    );

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public ALeylineOfResonance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // If A-Leyline of Resonance is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Whenever you cast an instant or sorcery spell that targets only a single creature you control, you may pay {1}. If you do, copy that spell. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new CopyTargetStackObjectEffect(),
                        new ManaCostsImpl<>("{1}")
                ),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private ALeylineOfResonance(final ALeylineOfResonance card) {
        super(card);
    }

    @Override
    public ALeylineOfResonance copy() {
        return new ALeylineOfResonance(this);
    }
}
