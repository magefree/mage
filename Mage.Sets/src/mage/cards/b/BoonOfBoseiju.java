package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoonOfBoseiju extends CardImpl {

    public BoonOfBoseiju(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature gets +X/+X until end of turn, where X is the greatest mana value among permanents you control. Untap that creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                BoonOfBoseijuValue.instance, BoonOfBoseijuValue.instance, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(BoonOfBoseijuValue.getHint());
    }

    private BoonOfBoseiju(final BoonOfBoseiju card) {
        super(card);
    }

    @Override
    public BoonOfBoseiju copy() {
        return new BoonOfBoseiju(this);
    }
}

enum BoonOfBoseijuValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("The greatest mana value among permanents you control", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT,
                sourceAbility.getControllerId(), sourceAbility.getSourceId(), game
        ).stream().mapToInt(MageObject::getManaValue).sum();
    }

    @Override
    public BoonOfBoseijuValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among permanents you control";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static Hint getHint() {
        return hint;
    }
}