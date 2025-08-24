package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class WillOfTheMardu extends CardImpl {

    public WillOfTheMardu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Create a number of 1/1 red Warrior creature tokens equal to the number of creatures target player controls.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new RedWarriorToken(), WillOfTheMarduValue.instance)
                .setText("Create a number of 1/1 red Warrior creature tokens equal to the number of creatures target player controls"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Will of the Mardu deals damage to target creature equal to the number of creatures you control.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(CreaturesYouControlCount.PLURAL)
                .setText("{this} deals damage to target creature equal to the number of creatures you control")).addTarget(new TargetCreaturePermanent()));
        this.getSpellAbility().addHint(WillOfTheMarduHint.instance);
    }

    private WillOfTheMardu(final WillOfTheMardu card) {
        super(card);
    }

    @Override
    public WillOfTheMardu copy() {
        return new WillOfTheMardu(this);
    }
}

enum WillOfTheMarduValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(effect.getTargetPointer().getFirst(game, sourceAbility));
        return player == null
                ? 0
                : game
                .getBattlefield()
                .count(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        player.getId(), sourceAbility, game
                );
    }

    @Override
    public WillOfTheMarduValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum WillOfTheMarduHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return game.getState()
                .getPlayersInRange(ability.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.getLogName() + ": " + game.getBattlefield().count(
                        StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), ability, game
                ))
                .collect(Collectors.joining(
                        ", ", "Creatures controlled by each player &mdash; ", ""
                ));
    }

    @Override
    public Hint copy() {
        return this;
    }
}
