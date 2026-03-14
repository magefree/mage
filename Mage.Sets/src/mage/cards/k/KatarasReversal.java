package mage.cards.k;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetStackObject;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KatarasReversal extends CardImpl {

    public KatarasReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter up to four target spells and/or abilities.
        this.getSpellAbility().addEffect(new CounterTargetEffect()
                .setText("counter up to four target spells and/or abilities"));
        this.getSpellAbility().addTarget(new TargetStackObject(
                0, 4, StaticFilters.FILTER_SPELL_OR_ABILITY
        ));

        // Untap up to four target artifacts and/or creatures.
        this.getSpellAbility().addEffect(new UntapTargetEffect()
                .setTargetPointer(new SecondTargetPointer())
                .setText("<br>Untap up to four target artifacts and/or creatures"));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 4, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE
        ));
    }

    private KatarasReversal(final KatarasReversal card) {
        super(card);
    }

    @Override
    public KatarasReversal copy() {
        return new KatarasReversal(this);
    }
}
