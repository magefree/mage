package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CatWarrior21Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AjaniFellsTheGodsire extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with power 3 or greater");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 3));
    }

    public AjaniFellsTheGodsire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile target creature an opponent controls with power 3 or greater.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetCreaturePermanent(filter)
        );

        // II -- Create a 2/1 white Cat Warrior creature token, then put a vigilance counter on a creature you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new CreateTokenEffect(new CatWarrior21Token()),
                new AjaniFellsTheGodsireCounterEffect()
        );

        // III -- Target creature you control gains double strike until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()),
                new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private AjaniFellsTheGodsire(final AjaniFellsTheGodsire card) {
        super(card);
    }

    @Override
    public AjaniFellsTheGodsire copy() {
        return new AjaniFellsTheGodsire(this);
    }
}

class AjaniFellsTheGodsireCounterEffect extends OneShotEffect {

    AjaniFellsTheGodsireCounterEffect() {
        super(Outcome.Benefit);
        staticText = ", then put a vigilance counter on a creature you control";
    }

    private AjaniFellsTheGodsireCounterEffect(final AjaniFellsTheGodsireCounterEffect effect) {
        super(effect);
    }

    @Override
    public AjaniFellsTheGodsireCounterEffect copy() {
        return new AjaniFellsTheGodsireCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.withChooseHint("to give a vigilance counter").withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.VIGILANCE.createInstance(), source, game);
    }
}