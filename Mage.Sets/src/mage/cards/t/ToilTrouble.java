
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.TargetPlayer;

public final class ToilTrouble extends SplitCard {

    public ToilTrouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}", "{2}{R}", SpellAbilityType.SPLIT_FUSED);

        // Toil
        // Target player draws two cards and loses 2 life.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPlayer().withChooseHint("to draw two cards and lose 2 life"));
        getLeftHalfCard().getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        getLeftHalfCard().getSpellAbility().addEffect(new LoseLifeTargetEffect(2));

        // Trouble
        // Trouble deals damage to target player equal to the number of cards in that player's hand.
        Effect effect = new DamageTargetEffect(CardsInTargetHandCount.instance);
        effect.setText("Trouble deals damage to target player equal to the number of cards in that player's hand");
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer().withChooseHint("to deal damage to"));

    }

    private ToilTrouble(final ToilTrouble card) {
        super(card);
    }

    @Override
    public ToilTrouble copy() {
        return new ToilTrouble(this);
    }
}
