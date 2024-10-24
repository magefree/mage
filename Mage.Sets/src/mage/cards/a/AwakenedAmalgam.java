package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AwakenedAmalgam extends CardImpl {

    public AwakenedAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Awakened Amalgam's power and toughness are each equal to the number of differently named lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(AwakenedAmalgamValue.instance)
        ).addHint(AwakenedAmalgamValue.getHint()));
    }

    private AwakenedAmalgam(final AwakenedAmalgam card) {
        super(card);
    }

    @Override
    public AwakenedAmalgam copy() {
        return new AwakenedAmalgam(this);
    }
}

enum AwakenedAmalgamValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Differently named lands you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.differentlyNamedAmongCollection(
                game.getBattlefield().getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                        sourceAbility.getControllerId(), sourceAbility, game
                ), game
        );
    }

    @Override
    public AwakenedAmalgamValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "differently named lands you control";
    }
}
