package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.StormCrowToken;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Murmuration extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BIRD, "Birds");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.BIRD, "");

    public Murmuration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Birds you control get +1/+1 and have vigilance.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).setText("and have vigilance"));
        this.addAbility(ability);

        // At the beginning of your end step, for each spell you've cast this turn, create a 1/2 blue Bird creature token with flying named Storm Crow.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new StormCrowToken(), MurmurationDynamicValue.instance)
                        .setText("for each spell you've cast this turn, create a " +
                                "1/2 blue Bird creature token with flying named Storm Crow"),
                TargetController.YOU, false
        ).addHint(MurmurationDynamicValue.getHint()));
    }

    private Murmuration(final Murmuration card) {
        super(card);
    }

    @Override
    public Murmuration copy() {
        return new Murmuration(this);
    }
}

enum MurmurationDynamicValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Spells you've cast this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public MurmurationDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn";
    }
}
