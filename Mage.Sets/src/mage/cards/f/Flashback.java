package mage.cards.f;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class Flashback extends CardImpl {

    public Flashback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.getSpellAbility().addEffect(new GainFlashbackTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
    }

    private Flashback(final Flashback card) {
        super(card);
    }

    @Override
    public Flashback copy() {
        return new Flashback(this);
    }
}
