package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;


/**
 *
 * @author muz
 */
public final class ObNixilisTheAscended extends CardImpl {

    private static final Condition condition = YouGainedLifeCondition.getZero();

    public ObNixilisTheAscended(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ob Nixilis enters, destroy all tapped creatures your opponents control. You gain 1 life for each creature destroyed this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ObNixilisTheAscendedEffect()));

        // At the beginning of each end step, if you gained life this turn, create a 4/4 white Angel creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY, new CreateTokenEffect(new AngelToken()), false
        ).withInterveningIf(condition).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private ObNixilisTheAscended(final ObNixilisTheAscended card) {
        super(card);
    }

    @Override
    public ObNixilisTheAscended copy() {
        return new ObNixilisTheAscended(this);
    }
}

class ObNixilisTheAscendedEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creatures your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    ObNixilisTheAscendedEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all tapped creatures your opponents control. You gain 1 life for each creature destroyed this way";
    }

    private ObNixilisTheAscendedEffect(final ObNixilisTheAscendedEffect effect) {
        super(effect);
    }

    @Override
    public ObNixilisTheAscendedEffect copy() {
        return new ObNixilisTheAscendedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
                if (creature.destroy(source, game, false)) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                game.processAction();
                controller.gainLife(destroyedCreature, game, source);
            }
            return true;
        }
        return false;
    }
}
