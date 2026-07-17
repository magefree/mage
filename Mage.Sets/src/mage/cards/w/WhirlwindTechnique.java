package mage.cards.w;

import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.abilities.effects.keyword.AirbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhirlwindTechnique extends CardImpl {

    public WhirlwindTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        this.subtype.add(SubType.LESSON);

        // Target player draws two cards, then discards a card.
        this.getSpellAbility().addEffect(new DrawDiscardTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Airbend up to two target creatures.
        this.getSpellAbility().addEffect(new AirbendTargetEffect()
                .setText("<br>Airbend up to two target creatures")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private WhirlwindTechnique(final WhirlwindTechnique card) {
        super(card);
    }

    @Override
    public WhirlwindTechnique copy() {
        return new WhirlwindTechnique(this);
    }
}
