
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author muz
 */
public final class JovenAndChandler extends CardImpl {

    private static final DynamicValue artifactManaValue = new JovenAndChandlerManaValue();

    public JovenAndChandler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an artifact is put into a graveyard from the battlefield, put a number of +1/+1 counters equal to that artifact's mana value on Joven and Chandler.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(), artifactManaValue, false)
                .setText("put a number of +1/+1 counters equal to that artifact's mana value on {this}"),
            false, StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, false
        ));

        // {R}{R}{R}, {T}: Gain control of target artifact until end of turn. Untap it. It gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new GainControlTargetEffect(Duration.EndOfTurn),
            new ManaCostsImpl<>("{R}{R}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent());
        ability.addEffect(new UntapTargetEffect().setText("Untap it"));
        ability.addEffect(new GainAbilityTargetEffect(
            HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn"));
        this.addAbility(ability);
    }

    private JovenAndChandler(final JovenAndChandler card) {
        super(card);
    }

    @Override
    public JovenAndChandler copy() {
        return new JovenAndChandler(this);
    }
}

class JovenAndChandlerManaValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability source, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("permanentDied");
        return permanent == null ? 0 : permanent.getManaValue();
    }

    @Override
    public DynamicValue copy() {
        return new JovenAndChandlerManaValue();
    }

    @Override
    public String getMessage() {
        return "that artifact's mana value";
    }
}
