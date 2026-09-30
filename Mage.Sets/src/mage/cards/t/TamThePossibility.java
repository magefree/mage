package mage.cards.t;

import java.util.Collection;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;

/**
 *
 * @author Grath
 */
public final class TamThePossibility extends CardImpl {

    private static final FilterCard filter = new FilterPlaneswalkerCard("planeswalker spells");

    public TamThePossibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Planeswalker spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // {W}{U}{B}{R}{G}, {T}: Proliferate X times, where X is the number of planeswalker types among planeswalkers you control.
        Ability ability = new SimpleActivatedAbility(new TamThePossibilityEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TamThePossibility(final TamThePossibility card) {
        super(card);
    }

    @Override
    public TamThePossibility copy() {
        return new TamThePossibility(this);
    }
}

class TamThePossibilityEffect extends OneShotEffect {

    TamThePossibilityEffect() {
        super(Outcome.Benefit);
        staticText = "proliferate X times, where X is the number of planeswalker types among planeswalkers you control";
    }

    private TamThePossibilityEffect(final TamThePossibilityEffect effect) {
        super(effect);
    }

    @Override
    public TamThePossibilityEffect copy() {
        return new TamThePossibilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = TamThePossibilityValue.instance.calculate(game, source, this);
        for (int i = 0; i < xValue; i++) {
            new ProliferateEffect().apply(game, source);
        }
        return xValue > 0;
    }
}

enum TamThePossibilityValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(permanent -> permanent.getSubtype(game))
                .flatMap(Collection::stream)
                .filter(subType -> SubType.getPlaneswalkerTypes().contains(subType))
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public TamThePossibilityValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}