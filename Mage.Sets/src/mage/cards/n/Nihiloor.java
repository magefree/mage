package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nihiloor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public Nihiloor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Nihiloor enters the battlefield, for each opponent, tap up to one untapped creature you control. When you do, gain control of target creature that player controls with power less than or equal to the tapped creature's power for as long as you control Nihiloor.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NihiloorControlEffect()));

        // Whenever you attack with a creature an opponent owns, you gain 2 life and that player loses 2 life.
        Ability ability = new AttacksAllTriggeredAbility(
                new GainLifeEffect(2), false, filter,
                SetTargetPointer.PERMANENT, false
        ).setTriggerPhrase("Whenever you attack with a creature an opponent owns, ");
        ability.addEffect(new NihiloorLoseLifeEffect());
        this.addAbility(ability);
    }

    private Nihiloor(final Nihiloor card) {
        super(card);
    }

    @Override
    public Nihiloor copy() {
        return new Nihiloor(this);
    }
}

class NihiloorControlEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("untapped creatured you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final class NihiloorPredicate implements Predicate<Permanent> {

        private final Permanent permanent;
        private final UUID playerId;

        private NihiloorPredicate(Permanent permanent, UUID playerId) {
            this.permanent = permanent;
            this.playerId = playerId;
        }

        @Override
        public boolean apply(Permanent input, Game game) {
            return input.isControlledBy(playerId)
                    && input.isCreature(game)
                    && input.getPower().getValue() <= permanent.getPower().getValue();
        }
    }

    NihiloorControlEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, tap up to one untapped creature you control. When you do, "
                + "gain control of target creature that player controls with power less than "
                + "or equal to the tapped creature's power for as long as you control {this}";
    }

    private NihiloorControlEffect(final NihiloorControlEffect effect) {
        super(effect);
    }

    @Override
    public NihiloorControlEffect copy() {
        return new NihiloorControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 1, filter, true);
            target.withChooseHint("tapping a creature controlled by " + opponent.getName());
            controller.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null || !permanent.tap(source, game)) {
                continue;
            }
            FilterPermanent filter2 = new FilterPermanent(
                    "creature controlled by " + opponent.getName()
                    + " with power " + permanent.getPower().getValue() + " or less"
            );
            filter2.add(new NihiloorPredicate(permanent, playerId));
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new GainControlTargetEffect(Duration.Custom, true),
                    false, "gain control of target creature that player controls with "
                    + "power less than or equal to the tapped creature's power for as long as you control {this}"
            );
            ability.addTarget(new TargetPermanent(filter2));
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }

}

class NihiloorLoseLifeEffect extends OneShotEffect {

    NihiloorLoseLifeEffect() {
        super(Outcome.Benefit);
        staticText = "and that player loses 2 life";
    }

    private NihiloorLoseLifeEffect(final NihiloorLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public NihiloorLoseLifeEffect copy() {
        return new NihiloorLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player owner = game.getPlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)));
        return owner != null
                && owner.loseLife(2, game, source, false) > 0;
    }
}
