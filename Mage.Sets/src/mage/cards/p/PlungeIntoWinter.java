package mage.cards.p;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlungeIntoWinter extends CardImpl {

    public PlungeIntoWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Tap up to one target creature. Scry 1, then draw a card.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private PlungeIntoWinter(final PlungeIntoWinter card) {
        super(card);
    }

    @Override
    public PlungeIntoWinter copy() {
        return new PlungeIntoWinter(this);
    }
}
