package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InnocentTraveler extends TransformingDoubleFacedCard {

    private static final FilterPermanent humanFilter = new FilterPermanent(SubType.HUMAN, "an opponent controls a Human");
    private static final Condition condition = new OpponentControlsPermanentCondition(humanFilter);
    private static final Hint hint = new ConditionHint(condition, "An opponent controls a Human");

    public InnocentTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{2}{B}{B}",
                "Malicious Invader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );

        // Innocent Traveler
        this.getLeftHalfCard().setPT(1, 3);

        // At the beginning of your upkeep, any opponent may sacrifice a creature. If no one does, transform Innocent Traveler.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new InnocentTravelerEffect()));

        // Malicious Invader
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Malicious Invader gets +2/+0 as long as an opponent controls a Human.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                condition, "{this} gets +2/+0 as long as an opponent controls a Human"
        )).addHint(hint));
    }

    private InnocentTraveler(final InnocentTraveler card) {
        super(card);
    }

    @Override
    public InnocentTraveler copy() {
        return new InnocentTraveler(this);
    }
}

class InnocentTravelerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature to sacrifice");

    InnocentTravelerEffect() {
        super(Outcome.Benefit);
        staticText = "any opponent may sacrifice a creature. If no one does, transform {this}";
    }

    private InnocentTravelerEffect(final InnocentTravelerEffect effect) {
        super(effect);
    }

    @Override
    public InnocentTravelerEffect copy() {
        return new InnocentTravelerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            TargetPermanent target = new TargetPermanent(filter);
            target.withNotTarget(true);
            if (!target.canChoose(opponent.getId(), source, game)
                    || !opponent.chooseUse(Outcome.AIDontUseIt, "Sacrifice a creature?", source, game)
                    || !opponent.choose(Outcome.Sacrifice, target, source, game)) {
                continue;
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null || !permanent.sacrifice(source, game)) {
                continue;
            }
            flag = true;
        }
        if (flag) {
            return true;
        }
        Permanent sourcePerm = source.getSourcePermanentIfItStillExists(game);
        if (sourcePerm != null) {
            sourcePerm.transform(source, game);
        }
        return true;
    }
}
