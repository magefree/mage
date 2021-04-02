package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeatedDebate extends CardImpl {

    public HeatedDebate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // This spell can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()
                .setText("This spell can't be countered. <i>(This includes by the ward ability.)</i>")
        ));

        // Heated Debate deals 4 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private HeatedDebate(final HeatedDebate card) {
        super(card);
    }

    @Override
    public HeatedDebate copy() {
        return new HeatedDebate(this);
    }
}
