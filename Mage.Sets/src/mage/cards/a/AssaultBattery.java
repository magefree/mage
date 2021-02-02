
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.game.permanent.token.ElephantToken;
import mage.target.common.TargetAnyTarget;

public final class AssaultBattery extends SplitCard {

    public AssaultBattery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}", "{3}{G}", SpellAbilityType.SPLIT);

        // Assault
        // Assault deals 2 damage to any target.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("Assault deals 2 damage to any target");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());

        // Battery
        // Create a 3/3 green Elephant creature token.
        getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()));

    }

    private AssaultBattery(final AssaultBattery card) {
        super(card);
    }

    @Override
    public AssaultBattery copy() {
        return new AssaultBattery(this);
    }
}
