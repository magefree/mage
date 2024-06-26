package mage.cards.e;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EagleVision extends CardImpl {

    public EagleVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Freerunning {1}{U}
        this.addAbility(new FreerunningAbility("{1}{U}"));

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private EagleVision(final EagleVision card) {
        super(card);
    }

    @Override
    public EagleVision copy() {
        return new EagleVision(this);
    }
}
