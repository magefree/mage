package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * Represents the "Wail of War" instant card.
 * Author: @mikejcunn
 */
public final class WailOfWar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures target opponent controls");

    static {
        filter.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public WailOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Mode 1: Creatures target player controls get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
            -1, -1, Duration.EndOfTurn, filter, false
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Mode 2: Return creatures from graveyard
        Mode returnCreaturesFromGraveyardMode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        returnCreaturesFromGraveyardMode.addTarget(new TargetCardInGraveyard(
            0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));
        this.getSpellAbility().addMode(returnCreaturesFromGraveyardMode);
    }

    private WailOfWar(final WailOfWar card) {
        super(card);
    }

    @Override
    public WailOfWar copy() {
        return new WailOfWar(this);
    }
}