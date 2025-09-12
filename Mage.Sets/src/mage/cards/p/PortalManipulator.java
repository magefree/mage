package mage.cards.p;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.Targets;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PortalManipulator extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false);
    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creatures controlled by that player's opponents");

    static {
        filter.add(PortalManipulatorPredicate.instance);
    }

    public PortalManipulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/U}{W/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Portal Manipulator enters the battlefield during the declare attackers step, choose target player and any number of target attacking creatures their opponents control. Those creatures are now attacking that player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PortalManipulatorEffect()).withTriggerCondition(condition);
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);
    }

    private PortalManipulator(final PortalManipulator card) {
        super(card);
    }

    @Override
    public PortalManipulator copy() {
        return new PortalManipulator(this);
    }
}

enum PortalManipulatorPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getSource)
                .map(Ability::getTargets)
                .map(Targets::getFirstTarget)
                .map(game::getPlayer)
                .filter(player -> player.hasOpponent(input.getObject().getControllerId(), game))
                .isPresent();
    }
}

class PortalManipulatorEffect extends OneShotEffect {

    PortalManipulatorEffect() {
        super(Outcome.Benefit);
        staticText = "choose target player and any number of target attacking creatures their opponents control. " +
                "Those creatures are now attacking that player";
        this.setTargetPointer(new EachTargetPointer());
    }

    private PortalManipulatorEffect(final PortalManipulatorEffect effect) {
        super(effect);
    }

    @Override
    public PortalManipulatorEffect copy() {
        return new PortalManipulatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        List<UUID> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().stream().anyMatch(permanents::contains)) {
                combatGroup.changeDefenderPostDeclaration(player.getId(), game);
            }
        }
        return true;
    }
}
