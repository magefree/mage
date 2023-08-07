package mage.cards.p;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Provoke extends CardImpl {

    public Provoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Untap target creature you don't control. That creature blocks this turn if able.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        Effect effect = new BlocksIfAbleTargetEffect(Duration.EndOfTurn);
        effect.setText("That creature blocks this turn if able");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Provoke(final Provoke card) {
        super(card);
    }

    @Override
    public Provoke copy() {
        return new Provoke(this);
    }
}
