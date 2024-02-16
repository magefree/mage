package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AvenCourier extends CardImpl {

    public AvenCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Aven Courier attacks, choose a counter on a permanent you control. Put a counter of that kind on target permanent you control if it doesn't have a counter of that kind on it.
        Ability ability = new AttacksTriggeredAbility(new AvenCourierEffect());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private AvenCourier(final AvenCourier card) {
        super(card);
    }

    @Override
    public AvenCourier copy() {
        return new AvenCourier(this);
    }
}

class AvenCourierEffect extends OneShotEffect {

    AvenCourierEffect() {
        super(Outcome.Benefit);
        staticText = "choose a counter on a permanent you control. Put a counter of that kind " +
                "on target permanent you control if it doesn't have a counter of that kind on it";
    }

    private AvenCourierEffect(final AvenCourierEffect effect) {
        super(effect);
    }

    @Override
    public AvenCourierEffect copy() {
        return new AvenCourierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Set<String> counterTypes = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT,
                        source.getControllerId(), source, game
                ).stream()
                .map(p -> p.getCounters(game))
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toSet());
        String counterType;
        switch (counterTypes.size()) {
            case 0:
                return false;
            case 1:
                counterType = counterTypes.iterator().next();
                break;
            case 2:
                Iterator<String> iterator = counterTypes.iterator();
                String type1 = iterator.next();
                String type2 = iterator.next();
                counterType = player.chooseUse(
                        outcome, "Choose a counter to put on " + permanent.getName(), null,
                        CardUtil.getTextWithFirstCharUpperCase(type1),
                        CardUtil.getTextWithFirstCharUpperCase(type2), source, game
                ) ? type1 : type2;
                break;
            default:
                Choice choice = new ChoiceImpl(true);
                choice.setChoices(counterTypes);
                player.choose(outcome, choice, game);
                counterType = choice.getChoice();
        }
        return permanent.getCounters(game).getCount(counterType) < 1
                && permanent.addCounters(CounterType.findByName(counterType).createInstance(), source, game);
    }
}
