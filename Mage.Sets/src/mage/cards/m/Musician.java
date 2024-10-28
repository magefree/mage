package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DynamicValueGenericManaCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth, Susucr
 */
public final class Musician extends CardImpl {

    public Musician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // : Put a music counter on target creature. If it doesn’t have "At the beginning of your upkeep, destroy this creature unless you pay {1} for each music counter on it," it gains that ability.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.MUSIC.createInstance()),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new MusicianEffect());
        this.addAbility(ability);
    }

    private Musician(final Musician card) {
        super(card);
    }

    @Override
    public Musician copy() {
        return new Musician(this);
    }
}

class MusicianEffect extends OneShotEffect {

    MusicianEffect() {
        super(Outcome.Benefit);
        staticText = "If it doesn't have \"At the beginning of your upkeep, " +
                "destroy this creature unless you pay {1} " +
                "for each music counter on it,\" it gains that ability";
    }

    private MusicianEffect(final MusicianEffect effect) {
        super(effect);
    }

    @Override
    public MusicianEffect copy() {
        return new MusicianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.getAbilities().stream().anyMatch(MusicianTriggeredAbility.class::isInstance)) {
            return true;
        }
        game.addEffect(new GainAbilityTargetEffect(new MusicianTriggeredAbility(), Duration.WhileOnBattlefield), source);
        return true;
    }
}

// This wrapper class is used to identify if the targeted creature already has the ability.
class MusicianTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    MusicianTriggeredAbility() {
        super(Zone.BATTLEFIELD, TargetController.YOU, new DoUnlessControllerPaysEffect(
                        new DestroySourceEffect(),
                        new DynamicValueGenericManaCost(new CountersSourceCount(CounterType.MUSIC),
                                "{1} for each music counter on {this}")
                ).setText("destroy this creature unless you pay {1} for each music counter on it"),
                false, false);
    }

    private MusicianTriggeredAbility(final MusicianTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MusicianTriggeredAbility copy() {
        return new MusicianTriggeredAbility(this);
    }
}
