package mage.cards.c;

import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class CunningStrike extends CardImpl {

    public CunningStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Cunning Strike deals 2 damage to target creature and 2 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetAndTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private CunningStrike(final CunningStrike card) {
        super(card);
    }

    @Override
    public CunningStrike copy() {
        return new CunningStrike(this);
    }
}
