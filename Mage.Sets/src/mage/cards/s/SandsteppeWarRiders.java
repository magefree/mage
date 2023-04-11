package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandsteppeWarRiders extends CardImpl {

    public SandsteppeWarRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, bolster X, where X is the number of differently named artifact tokens you control.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BolsterEffect(SandsteppeWarRidersValue.instance), TargetController.YOU, false));
    }

    private SandsteppeWarRiders(final SandsteppeWarRiders card) {
        super(card);
    }

    @Override
    public SandsteppeWarRiders copy() {
        return new SandsteppeWarRiders(this);
    }
}

enum SandsteppeWarRidersValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final Hint hint = new ValueHint("Different artifact token names you control", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public SandsteppeWarRidersValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "differently named artifact tokens you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
