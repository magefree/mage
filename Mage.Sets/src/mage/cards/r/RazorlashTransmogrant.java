package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Controllable;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class RazorlashTransmogrant extends CardImpl {

    public RazorlashTransmogrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Razorlash Transmogrant can't block.
        this.addAbility(new CantBlockAbility());

        // {4}{B}{B}: Return Razorlash Transmogrant from your graveyard to the battlefield with a +1/+1 counter on it. This ability costs {4} less to activate if an opponent controls four or more nonbasic lands.
        Ability ability = new SimpleActivatedAbility(
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                        CounterType.P1P1.createInstance(), false
                ), new ManaCostsImpl<>("{4}{B}{B}")
        );
        ability.addEffect(new InfoEffect("This ability costs {4} less to activate if an opponent controls four or more nonbasic lands"));
        this.addAbility(ability.setCostAdjuster(RazorlashTransmograntAdjuster.instance).addHint(RazorlashTransmograntHint.instance));
    }

    private RazorlashTransmogrant(final RazorlashTransmogrant card) {
        super(card);
    }

    @Override
    public RazorlashTransmogrant copy() {
        return new RazorlashTransmogrant(this);
    }
}

enum RazorlashTransmograntAdjuster implements CostAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (makeMap(game, ability).values().stream().anyMatch(x -> x >= 4)) {
            CardUtil.reduceCost(ability, 4);
        }
    }

    static Map<UUID, Integer> makeMap(Game game, Ability ability) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, ability.getControllerId(), ability, game)
                .stream()
                .collect(Collectors.toMap(
                        Controllable::getControllerId,
                        x -> 1, Integer::sum
                ));
    }
}

enum RazorlashTransmograntHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        return RazorlashTransmograntAdjuster
                .makeMap(game, ability)
                .entrySet()
                .stream()
                .map(entry -> game.getPlayer(entry.getKey()).getLogName() + ": " + entry.getValue() + " nonbasic lands")
                .collect(Collectors.joining("<br>"));
    }

    @Override
    public RazorlashTransmograntHint copy() {
        return this;
    }
}
