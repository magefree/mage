package mage.cards.i;

import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class InterdisciplinaryStudies extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored card");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public InterdisciplinaryStudies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Seek a multicolored card.
        this.getSpellAbility().addEffect(new SeekCardEffect(filter));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);

    }

    private InterdisciplinaryStudies(final InterdisciplinaryStudies card) {
        super(card);
    }

    @Override
    public InterdisciplinaryStudies copy() {
        return new InterdisciplinaryStudies(this);
    }
}
