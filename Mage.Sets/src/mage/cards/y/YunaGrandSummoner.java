package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YunaGrandSummoner extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CounterAnyPredicate.instance);
    }

    public YunaGrandSummoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Grand Summon -- {T}: Add one mana of any color. When you next cast a creature spell this turn, that creature enters with two additional +1/+1 counters on it.
        AnyColorManaAbility manaAbility = new AnyColorManaAbility();
        manaAbility.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AddCounterNextSpellDelayedTriggeredAbility(2, StaticFilters.FILTER_SPELL_A_CREATURE)
        ));
        manaAbility.setUndoPossible(false);
        this.addAbility(manaAbility.withFlavorWord("Grand Summon"));

        // Whenever another permanent you control is put into a graveyard from the battlefield, if it had one or more counters on it, you may put that number of +1/+1 counters on target creature.
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), YunaGrandSummonerValue.instance)
                        .setText("if it had one or more counters on it, " +
                                "you may put that number of +1/+1 counters on target creature"),
                true, filter, false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private YunaGrandSummoner(final YunaGrandSummoner card) {
        super(card);
    }

    @Override
    public YunaGrandSummoner copy() {
        return new YunaGrandSummoner(this);
    }
}

enum YunaGrandSummonerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable((Permanent) effect.getValue("permanentDied"))
                .map(permanent -> permanent.getCounters(game))
                .map(Counters::getTotalCount)
                .orElse(0);
    }

    @Override
    public YunaGrandSummonerValue copy() {
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
