package mage.cards.j;

import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MonasteryMentorToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetSpellOrPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeskaiRevelation extends CardImpl {

    public JeskaiRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{R}{W}");

        // Return target spell or permanent to its owner's hand. Jeskai Revelation deals 4 damage to any target. Create two 1/1 white Monk creature tokens with prowess. Draw two cards. You gain 4 life.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                4, true, "any target", true
        ).setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MonasteryMentorToken(), 2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    private JeskaiRevelation(final JeskaiRevelation card) {
        super(card);
    }

    @Override
    public JeskaiRevelation copy() {
        return new JeskaiRevelation(this);
    }
}
