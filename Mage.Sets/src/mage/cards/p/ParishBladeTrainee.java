package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParishBladeTrainee extends CardImpl {

    public ParishBladeTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Training
        this.addAbility(new TrainingAbility());

        // When Parish-Blade Trainee dies, put its counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new ParishBladeTraineeEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ParishBladeTrainee(final ParishBladeTrainee card) {
        super(card);
    }

    @Override
    public ParishBladeTrainee copy() {
        return new ParishBladeTrainee(this);
    }
}

class ParishBladeTraineeEffect extends OneShotEffect {

    ParishBladeTraineeEffect() {
        super(Outcome.Benefit);
        staticText = "put its counters on target creature you control";
    }

    private ParishBladeTraineeEffect(final ParishBladeTraineeEffect effect) {
        super(effect);
    }

    @Override
    public ParishBladeTraineeEffect copy() {
        return new ParishBladeTraineeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = (Permanent) getValue("permanentLeftBattlefield");
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        sourcePermanent
                .getCounters(game)
                .values()
                .stream()
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }
}
