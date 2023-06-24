package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LinvalaShieldOfSeaGate extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public LinvalaShieldOfSeaGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, if you have a full party, choose target nonland permanent an opponent controls. Until your next turn, it can't attack or block, and its activated abilities can't be activated.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new LinvalaShieldOfSeaGateRestrictionEffect(),
                        TargetController.YOU, false
                ), FullPartyCondition.instance, "At the beginning of combat on your turn, " +
                "if you have a full party, choose target nonland permanent an opponent controls. " +
                "Until your next turn, it can't attack or block, and its activated abilities can't be activated."
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.addHint(PartyCountHint.instance));

        // Sacrifice Linvala: Choose hexproof or indestructible. Creatures you control gain that ability until end of turn.
        this.addAbility(new SimpleActivatedAbility(new LinvalaShieldOfSeaGateEffect(), new SacrificeSourceCost()));
    }

    private LinvalaShieldOfSeaGate(final LinvalaShieldOfSeaGate card) {
        super(card);
    }

    @Override
    public LinvalaShieldOfSeaGate copy() {
        return new LinvalaShieldOfSeaGate(this);
    }
}

class LinvalaShieldOfSeaGateRestrictionEffect extends RestrictionEffect {

    LinvalaShieldOfSeaGateRestrictionEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
    }

    LinvalaShieldOfSeaGateRestrictionEffect(final LinvalaShieldOfSeaGateRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public LinvalaShieldOfSeaGateRestrictionEffect copy() {
        return new LinvalaShieldOfSeaGateRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }
}

class LinvalaShieldOfSeaGateEffect extends OneShotEffect {

    LinvalaShieldOfSeaGateEffect() {
        super(Outcome.Benefit);
        staticText = "Choose hexproof or indestructible. Creatures you control gain that ability until end of turn.";
    }

    private LinvalaShieldOfSeaGateEffect(final LinvalaShieldOfSeaGateEffect effect) {
        super(effect);
    }

    @Override
    public LinvalaShieldOfSeaGateEffect copy() {
        return new LinvalaShieldOfSeaGateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability = player.chooseUse(
                Outcome.Neutral, "Choose hexproof or indestructible", null,
                "Hexproof", "Indestructible", source, game
        ) ? HexproofAbility.getInstance() : IndestructibleAbility.getInstance();
        game.addEffect(new GainAbilityControlledEffect(
                ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ), source);
        return true;
    }
}
