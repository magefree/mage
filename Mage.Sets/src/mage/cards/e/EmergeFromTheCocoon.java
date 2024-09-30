package mage.cards.e;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergeFromTheCocoon extends CardImpl {

    public EmergeFromTheCocoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Return target creature card from your graveyard to the battlefield. You gain 3 life.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private EmergeFromTheCocoon(final EmergeFromTheCocoon card) {
        super(card);
    }

    @Override
    public EmergeFromTheCocoon copy() {
        return new EmergeFromTheCocoon(this);
    }
}
