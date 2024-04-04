package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class QuickDraw extends CardImpl {

    public QuickDraw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature you control gets +1/+1 and gains first strike until end of turn. Creatures target opponent controls lose first strike and double strike until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1)
                .setText("Target creature you control gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText(" and gains first strike until end of turn."));
        this.getSpellAbility().addEffect(new QuickDrawEffect().setTargetPointer(new SecondTargetPointer()));
    }

    private QuickDraw(final QuickDraw card) {
        super(card);
    }

    @Override
    public QuickDraw copy() {
        return new QuickDraw(this);
    }
}

class QuickDrawEffect extends OneShotEffect {

    QuickDrawEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Creatures target opponent controls lose first strike and double strike until end of turn.";
    }

    private QuickDrawEffect(final QuickDrawEffect effect) {
        super(effect);
    }

    @Override
    public QuickDrawEffect copy() {
        return new QuickDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }

        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(opponent.getId()));
        game.addEffect(new LoseAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter), source);
        game.addEffect(new LoseAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter), source);
        return true;
    }

}