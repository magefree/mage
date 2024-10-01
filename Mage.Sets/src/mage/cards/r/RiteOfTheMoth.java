package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiteOfTheMoth extends CardImpl {

    public RiteOfTheMoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{B}{B}");

        // Return target creature card from your graveyard to the battlefield with a finality counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // Flashback {3}{W}{W}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{W}{W}{B}")));
    }

    private RiteOfTheMoth(final RiteOfTheMoth card) {
        super(card);
    }

    @Override
    public RiteOfTheMoth copy() {
        return new RiteOfTheMoth(this);
    }
}
