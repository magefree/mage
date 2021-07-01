package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoureAmbushedOnTheRoad extends CardImpl {

    public YoureAmbushedOnTheRoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one —
        // • Make a Retreat — Return target creature you control to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Make a Retreat");

        // • Stand and Fight — Target creature gets +1/+3 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(1, 3));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode.withFlavorWord("Stand and Fight"));
    }

    private YoureAmbushedOnTheRoad(final YoureAmbushedOnTheRoad card) {
        super(card);
    }

    @Override
    public YoureAmbushedOnTheRoad copy() {
        return new YoureAmbushedOnTheRoad(this);
    }
}
