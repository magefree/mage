package mage.cards.a;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlluringSuitor extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AlluringSuitor(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "{2}{R}",
                "Deadly Dancer",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "R"
        );
        this.getLeftHalfCard().setPT(2, 3);
        this.getRightHalfCard().setPT(3, 3);

        // When you attack with exactly two creatures, transform Alluring Suitor.
        this.getLeftHalfCard().addAbility(new AlluringSuitorTriggeredAbility());

        // Deadly Dancer
        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // When this creature transforms into Deadly Dancer, add {R}{R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(new DeadlyDancerEffect()));

        // {R}{R}: Deadly Dancer and another target creature each get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this}"), new ManaCostsImpl<>("{R}{R}"));
        ability.addEffect(new BoostTargetEffect(1, 0)
                .setText("and another target creature each get +1/+0 until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private AlluringSuitor(final AlluringSuitor card) {
        super(card);
    }

    @Override
    public AlluringSuitor copy() {
        return new AlluringSuitor(this);
    }
}

class AlluringSuitorTriggeredAbility extends TriggeredAbilityImpl {

    AlluringSuitorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    private AlluringSuitorTriggeredAbility(final AlluringSuitorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AlluringSuitorTriggeredAbility copy() {
        return new AlluringSuitorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(game.getCombat().getAttackingPlayerId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(permanent -> permanent.isCreature(game))
                .count() == 2;
    }

    @Override
    public String getRule() {
        return "When you attack with exactly two creatures, transform {this}.";
    }
}

class DeadlyDancerEffect extends OneShotEffect {

    DeadlyDancerEffect() {
        super(Outcome.Benefit);
        staticText = "add {R}{R}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private DeadlyDancerEffect(final DeadlyDancerEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyDancerEffect copy() {
        return new DeadlyDancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getManaPool().addMana(Mana.RedMana(2), game, source, true);
        return true;
    }
}
