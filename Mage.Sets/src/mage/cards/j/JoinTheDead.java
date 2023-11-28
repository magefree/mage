package mage.cards.j;

import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JoinTheDead extends CardImpl {

    public JoinTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Target creature gets -5/-5 until end of turn.
        // Descend 4 -- That creature gets -10/-10 until end of turn instead if there are four or more permanent cards in your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-10, -10)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-5, -5)),
                DescendCondition.FOUR,
                "Target creature gets -5/-5 until end of turn."
                        + "<br><i>Descend 4</i> &mdash; That creature gets -10/-10 until end of turn instead "
                        + "if there are four or more permanent cards in your graveyard."
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(DescendCondition.getHint());
    }

    private JoinTheDead(final JoinTheDead card) {
        super(card);
    }

    @Override
    public JoinTheDead copy() {
        return new JoinTheDead(this);
    }
}
