
package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class EvolutionCharm extends CardImpl {

    public EvolutionCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one - Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library;
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND), true, true));

        // or return target creature card from your graveyard to your hand;
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addMode(mode);

        // or target creature gains flying until end of turn.
        mode = new Mode();
        mode.getEffects().add(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    public EvolutionCharm(final EvolutionCharm card) {
        super(card);
    }

    @Override
    public EvolutionCharm copy() {
        return new EvolutionCharm(this);
    }
}
