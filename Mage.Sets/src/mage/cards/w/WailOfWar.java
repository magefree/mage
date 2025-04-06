package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.effects.common.BoostControlledEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * Represents the "Wail of War" instant card.
 * Author: @mikejcunn
 */
public final class WailOfWar extends CardImpl {

    public WailOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Mode 1: Debuff opponent's creatures
        Mode debuffOpponentCreaturesMode = new Mode(new BoostControlledEffect(
            -1, -1, Duration.EndOfTurn, 
            new FilterCreaturePermanent("creatures target opponent controls")
        ));
        debuffOpponentCreaturesMode.addTarget(new TargetPlayer()); // Target a single opponent
        debuffOpponentCreaturesMode.setText("Creatures target opponent controls get -1/-1 until end of turn.");
        this.getSpellAbility().addMode(debuffOpponentCreaturesMode);

        // Mode 2: Return creatures from graveyard
        Mode returnCreaturesFromGraveyardMode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        returnCreaturesFromGraveyardMode.addTarget(new TargetCreaturePermanent(
            0, 2, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, false
        ));
        returnCreaturesFromGraveyardMode.setText("Return up to two target creatures from your graveyard to your hand.");
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