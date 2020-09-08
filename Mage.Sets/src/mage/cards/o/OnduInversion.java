package mage.cards.o;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnduInversion extends CardImpl {

    public OnduInversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.o.OnduInversion.class;

        // Destroy all nonland permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
    }

    private OnduInversion(final OnduInversion card) {
        super(card);
    }

    @Override
    public OnduInversion copy() {
        return new OnduInversion(this);
    }
}
