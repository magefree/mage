package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AstarionTheDecadent extends CardImpl {

    public AstarionTheDecadent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, choose one —
        // • Feed — Target opponent loses life equal to the amount of life they lost this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new LoseLifeTargetEffect(AstarionTheDecadentValue.instance), TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        ability.withFirstModeFlavorWord("Feed");
        ability.addHint(ControllerGotLifeCount.getHint());

        // • Friends — You gain life equal to the amount of life you gained this turn.
        ability.addMode(new Mode(new GainLifeEffect(ControllerGotLifeCount.instance)).withFlavorWord("Friends"));
        this.addAbility(ability.addHint(AstarionTheDecadentHint.instance), new PlayerGainedLifeWatcher());
    }

    private AstarionTheDecadent(final AstarionTheDecadent card) {
        super(card);
    }

    @Override
    public AstarionTheDecadent copy() {
        return new AstarionTheDecadent(this);
    }
}

enum AstarionTheDecadentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(effect.getTargetPointer().getFirst(game, sourceAbility));
    }

    @Override
    public AstarionTheDecadentValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the amount of life they lost this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum AstarionTheDecadentHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return "Life lost for each player: " + game
                .getOpponents(ability.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.getName() + " (" + watcher.getLifeLost(player.getId()) + ')')
                .collect(Collectors.joining(", "));
    }

    @Override
    public AstarionTheDecadentHint copy() {
        return this;
    }
}
