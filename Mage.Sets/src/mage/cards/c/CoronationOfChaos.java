package mage.cards.c;

import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoronationOfChaos extends CardImpl {

    public CoronationOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Up to three target creatures can't block this turn. Goad them.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new GoadTargetEffect().setText("Goad them"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private CoronationOfChaos(final CoronationOfChaos card) {
        super(card);
    }

    @Override
    public CoronationOfChaos copy() {
        return new CoronationOfChaos(this);
    }
}
