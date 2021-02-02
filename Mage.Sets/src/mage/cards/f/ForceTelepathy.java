package mage.cards.f;

import java.util.UUID;

import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class ForceTelepathy extends CardImpl {

    public ForceTelepathy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/B}");
        

        // Target player reveals their hand.
        this.getSpellAbility().addEffect(new RevealHandTargetEffect().setText("Target player reveals their hand"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Scry 2
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private ForceTelepathy(final ForceTelepathy card) {
        super(card);
    }

    @Override
    public ForceTelepathy copy() {
        return new ForceTelepathy(this);
    }
}
