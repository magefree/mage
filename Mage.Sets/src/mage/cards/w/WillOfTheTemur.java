package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillOfTheTemur extends CardImpl {

    public WillOfTheTemur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Create a token that's a copy of target permanent, except it's a 4/4 Dragon creature with flying in addition to its other types.
        this.getSpellAbility().addEffect(new WillOfTheTemurEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // * Target player draws cards equal to the greatest mana value among permanents you control.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(WillOfTheTemurValue.instance)
                .setText("target player draws cards equal to the greatest mana value among permanents you control")
        ).addTarget(new TargetPlayer()));
        this.getSpellAbility().addHint(WillOfTheTemurValue.getHint());
    }

    private WillOfTheTemur(final WillOfTheTemur card) {
        super(card);
    }

    @Override
    public WillOfTheTemur copy() {
        return new WillOfTheTemur(this);
    }
}

enum WillOfTheTemurValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Greatest mana value among permanents you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public WillOfTheTemurValue copy() {
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

class WillOfTheTemurEffect extends OneShotEffect {

    WillOfTheTemurEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target permanent, " +
                "except it's a 4/4 Dragon creature with flying in addition to its other types";
    }

    private WillOfTheTemurEffect(final WillOfTheTemurEffect effect) {
        super(effect);
    }

    @Override
    public WillOfTheTemurEffect copy() {
        return new WillOfTheTemurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        return new CreateTokenCopyTargetEffect().setPermanentModifier(token -> {
            token.addCardType(CardType.CREATURE);
            token.addSubType(SubType.DRAGON);
            token.setPower(4);
            token.setToughness(4);
            token.addAbility(FlyingAbility.getInstance());
        }).setSavedPermanent(permanent).apply(game, source);
    }
}
