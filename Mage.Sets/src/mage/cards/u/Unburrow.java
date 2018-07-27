package mage.cards.u;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZergToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author NinthWorld
 */
public final class Unburrow extends CardImpl {

    public Unburrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");
        

        // Choose one -
        //   Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE));

        //   Put two 1/1 green Zerg creature tokens onto the battlefield.
        Mode mode = new Mode();
        mode.getEffects().add(new CreateTokenEffect(new ZergToken(), 2));
        this.getSpellAbility().addMode(mode);

        //   Creatures you control get +1/+1 until end of turn.
        mode = new Mode();
        mode.getEffects().add(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addMode(mode);
    }

    public Unburrow(final Unburrow card) {
        super(card);
    }

    @Override
    public Unburrow copy() {
        return new Unburrow(this);
    }
}
