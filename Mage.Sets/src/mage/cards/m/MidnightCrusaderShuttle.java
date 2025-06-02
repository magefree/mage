package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightCrusaderShuttle extends CardImpl {

    public MidnightCrusaderShuttle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Midnight Entity -- Whenever Midnight Crusader Shuttle attacks, defending player faces a villainous choice -- That player sacrifices a creature, or you gain control of a creature of your choice that player controls until end of turn. If you gain control of a creature this way, tap it, and it's attacking that player.
        this.addAbility(new AttacksTriggeredAbility(
                new MidnightCrusaderShuttleEffect(),
                false, null, SetTargetPointer.PLAYER
        ).withFlavorWord("Midnight Entity"));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MidnightCrusaderShuttle(final MidnightCrusaderShuttle card) {
        super(card);
    }

    @Override
    public MidnightCrusaderShuttle copy() {
        return new MidnightCrusaderShuttle(this);
    }
}

class MidnightCrusaderShuttleEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Sacrifice,
            new MidnightCrusaderShuttleFirstChoice(),
            new MidnightCrusaderShuttleSecondChoice()
    );

    MidnightCrusaderShuttleEffect() {
        super(Outcome.Benefit);
        staticText = "defending player " + choice.generateRule();
    }

    private MidnightCrusaderShuttleEffect(final MidnightCrusaderShuttleEffect effect) {
        super(effect);
    }

    @Override
    public MidnightCrusaderShuttleEffect copy() {
        return new MidnightCrusaderShuttleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .ifPresent(player -> choice.faceChoice(player, game, source));
        return true;
    }
}

class MidnightCrusaderShuttleFirstChoice extends VillainousChoice {

    MidnightCrusaderShuttleFirstChoice() {
        super("That player sacrifices a creature of their choice", "Sacrifice a creature");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)
                .pay(source, game, source, player.getId(), true);
    }
}

class MidnightCrusaderShuttleSecondChoice extends VillainousChoice {

    MidnightCrusaderShuttleSecondChoice() {
        super(
                "you gain control of a creature of your choice that player controls until end of turn. " +
                        "If you gain control of a creature this way, tap it, and it's attacking that player",
                "{controller} gains control of one of your creatures and it attacks you"
        );
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game, 1
        )) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");
        filter.add(new ControllerIdPredicate(player.getId()));
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        controller.choose(Outcome.GainControl, target, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn, true)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.processAction();
        if (!permanent.isControlledBy(controller.getId())) {
            return false;
        }
        permanent.tap(source, game);
        game.getCombat().addAttackingCreature(permanent.getId(), game, player.getId());
        return true;
    }
}
