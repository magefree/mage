package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LiveOrDie extends CardImpl {

    public LiveOrDie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Choose one --
        // * Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // * Destroy target creature.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetCreaturePermanent()));
    }

    private LiveOrDie(final LiveOrDie card) {
        super(card);
    }

    @Override
    public LiveOrDie copy() {
        return new LiveOrDie(this);
    }
}
