package mage.cards.p;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PucasEye extends CardImpl {

    public PucasEye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When this artifact enters, draw a card, then choose a color. This artifact becomes the chosen color.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PucasEyeConditionEffect());
        this.addAbility(ability);

        // {3}, {T}: Draw a card. Activate only if there are five colors among permanents you control.
        ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(3), PucasEyeCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint()));
    }

    private PucasEye(final PucasEye card) {
        super(card);
    }

    @Override
    public PucasEye copy() {
        return new PucasEye(this);
    }
}

enum PucasEyeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.calculate(game, source, null) == 5;
    }

    @Override
    public String toString() {
        return "there are five colors among permanents you control";
    }
}

class PucasEyeConditionEffect extends OneShotEffect {

    PucasEyeConditionEffect() {
        super(Outcome.Benefit);
        staticText = ", then choose a color. {this} becomes the chosen color";
    }

    private PucasEyeConditionEffect(final PucasEyeConditionEffect effect) {
        super(effect);
    }

    @Override
    public PucasEyeConditionEffect copy() {
        return new PucasEyeConditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true);
        player.choose(outcome, choice, game);
        ObjectColor color = choice.getColor();
        if (color == null) {
            return false;
        }
        game.informPlayers(CardUtil.getSourceLogName(game, source) + ": " + player.getLogName() + " chooses " + color.getDescription());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BecomesColorTargetEffect(color, false, Duration.Custom)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
