package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.SalamnderWarriorToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GorMuldrakAmphinologist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SALAMANDER, "Salamanders");

    public GorMuldrakAmphinologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You and permanents you control have protection from Salamanders.
        Ability ability = new SimpleStaticAbility(
                new GainAbilityControllerEffect(new ProtectionAbility(filter)).setText("you")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                new ProtectionAbility(filter), Duration.WhileOnBattlefield
        ).concatBy("and"));
        this.addAbility(ability);

        // At the beginning of your end step, each player who controls the fewest creatures creates a 4/3 blue Salamander Warrior creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GorMuldrakAmphinologistEffect(), TargetController.YOU, false
        ));
    }

    private GorMuldrakAmphinologist(final GorMuldrakAmphinologist card) {
        super(card);
    }

    @Override
    public GorMuldrakAmphinologist copy() {
        return new GorMuldrakAmphinologist(this);
    }
}

class GorMuldrakAmphinologistEffect extends OneShotEffect {

    GorMuldrakAmphinologistEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls the fewest creatures " +
                "creates a 4/3 blue Salamander Warrior creature token";
    }

    private GorMuldrakAmphinologistEffect(final GorMuldrakAmphinologistEffect effect) {
        super(effect);
    }

    @Override
    public GorMuldrakAmphinologistEffect copy() {
        return new GorMuldrakAmphinologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> creatureMap = new HashMap<>();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .forEach(uuid -> creatureMap.put(uuid, 0));
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        source.getControllerId(), game
                ).stream()
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .forEach(uuid -> creatureMap.compute(uuid, CardUtil::setOrIncrementValue));
        int minValue = creatureMap.values().stream().mapToInt(x -> x).min().orElse(0);
        minValue = Math.max(minValue, 0);
        Token token = new SalamnderWarriorToken();
        for (Map.Entry<UUID, Integer> entry : creatureMap.entrySet()) {
            if (entry.getValue() > minValue) {
                continue;
            }
            token.putOntoBattlefield(1, game, source, entry.getKey());
        }
        return true;
    }
}
