package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DestinySpinner extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Creature and enchantment spells you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public DestinySpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Creature and enchantment spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                filter, null, Duration.WhileOnBattlefield
        )));

        // {3}{G}: Target land you control becomes an X/X Elemental creature with trample and haste until end of turn, where X is the number of enchantments you control. It's still a land.
        Ability ability = new SimpleActivatedAbility(new DestinySpinnerEffect(), new ManaCostsImpl<>("{3}{G}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        ability.addHint(new ValueHint("Enchantments you control", DestinySpinnerCount.instance));
        this.addAbility(ability);
    }

    private DestinySpinner(final DestinySpinner card) {
        super(card);
    }

    @Override
    public DestinySpinner copy() {
        return new DestinySpinner(this);
    }
}

enum DestinySpinnerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, sourceAbility.getControllerId(), game);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class DestinySpinnerEffect extends OneShotEffect {

    DestinySpinnerEffect() {
        super(Outcome.Benefit);
        staticText = "Target land you control becomes an X/X Elemental creature with trample and haste " +
                "until end of turn, where X is the number of enchantments you control. It's still a land.";
    }

    private DestinySpinnerEffect(final DestinySpinnerEffect effect) {
        super(effect);
    }

    @Override
    public DestinySpinnerEffect copy() {
        return new DestinySpinnerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int pt = DestinySpinnerCount.instance.calculate(game, source, this);
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(pt, pt, "")
                        .withSubType(SubType.ELEMENTAL)
                        .withAbility(TrampleAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ), source);
        return true;
    }
}