package mage.cards.b;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroadsideBarrage extends CardImpl {

    public BroadsideBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Broadside Barrage deals 5 damage to target creature or planeswalker. Draw a card, then discard a card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(1, 1));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BroadsideBarrage(final BroadsideBarrage card) {
        super(card);
    }

    @Override
    public BroadsideBarrage copy() {
        return new BroadsideBarrage(this);
    }
}
