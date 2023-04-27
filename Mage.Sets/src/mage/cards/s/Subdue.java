package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Subdue extends CardImpl {

    public Subdue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Prevent all combat damage that would be dealt by target creature this turn. That creature gets +0/+X until end of turn, where X is its converted mana cost.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addEffect(new BoostTargetEffect(StaticValue.get(0), TargetManaValue.instance, Duration.EndOfTurn)
                .setText("That creature gets +0/+X until end of turn, where X is its mana value"));
    }

    private Subdue(final Subdue card) {
        super(card);
    }

    @Override
    public Subdue copy() {
        return new Subdue(this);
    }
}
