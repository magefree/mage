package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GluttonousHellkite extends CardImpl {

    public GluttonousHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{B}{R}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield with two +1/+1 counters on it for each creature sacrificed this way.
        this.addAbility(new CastSourceTriggeredAbility(new SacrificeAllEffect(GetXValue.instance, StaticFilters.FILTER_PERMANENT_CREATURES)));
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), GluttonousHellkiteDynamicValue.instance, true),
                "with two +1/+1 counters on it for each creature sacrificed this way");
        ability.addHint(new ValueHint("Will get +1/+1 counters on ETB", GluttonousHellkiteDynamicValue.instance));
        //ability.setRuleVisible(false);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private GluttonousHellkite(final GluttonousHellkite card) {
        super(card);
    }

    @Override
    public GluttonousHellkite copy() {
        return new GluttonousHellkite(this);
    }
}

enum GluttonousHellkiteDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        List<Permanent> list = SacrificeAllEffect.getSacrificedPermanentsList(sourceAbility.getSourceId(), game, false);
        if (list == null) {
            return 0;
        }
        return list.size() * 2;
    }

    @Override
    public GluttonousHellkiteDynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "will get counters";
    }
}