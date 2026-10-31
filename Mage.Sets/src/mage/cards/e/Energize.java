package mage.cards.e;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class Energize extends CardImpl {

    public Energize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Return target creature an opponent controls to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // Beam me up {3}{U}
        this.addAbility(new BeamMeUpAbility(this, "{3}{U}"));
    }

    private Energize(final Energize card) {
        super(card);
    }

    @Override
    public Energize copy() {
        return new Energize(this);
    }
}
