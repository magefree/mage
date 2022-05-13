package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class FindFinality extends SplitCard {

    public FindFinality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B/G}{B/G}", "{4}{B}{G}", SpellAbilityType.SPLIT);

        // Find
        // Return up to two target creature cards from your graveyard to your hand.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new ReturnFromGraveyardToHandTargetEffect()
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD)
        );

        // Finality
        // You may put two +1/+1 counters on a creature you control. Then all creatures get -4/-4 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new FinalityEffect()
        );
    }

    private FindFinality(final FindFinality card) {
        super(card);
    }

    @Override
    public FindFinality copy() {
        return new FindFinality(this);
    }
}

class FinalityEffect extends OneShotEffect {

    public FinalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put two +1/+1 counters "
                + "on a creature you control. "
                + "Then all creatures get -4/-4 until end of turn.";
    }

    public FinalityEffect(final FinalityEffect effect) {
        super(effect);
    }

    @Override
    public FinalityEffect copy() {
        return new FinalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent(0, 1);
        target.setNotTarget(true);
        if (player.choose(
                Outcome.BoostCreature, target, source, game
        )) {
            Effect effect = new AddCountersTargetEffect(
                    CounterType.P1P1.createInstance(2)
            );
            effect.setTargetPointer(new FixedTarget(
                    target.getFirstTarget(), game
            ));
            effect.apply(game, source);
        }
        game.addEffect(new BoostAllEffect(-4, -4, Duration.EndOfTurn), source);
        return true;
    }
}
