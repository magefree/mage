package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Angel33Token;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SoulOfEmancipation extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other nonland permanents");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SoulOfEmancipation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}{U}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // When Soul of Emancipation enters the battlefield, destroy up to three other target nonland permanents. For each of those permanents, its controller creates a 3/3 white Angel creature token with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SoulOfEmancipationEffect());
        ability.addTarget(new TargetPermanent(0, 3, filter));
        this.addAbility(ability);
    }

    private SoulOfEmancipation(final SoulOfEmancipation card) {
        super(card);
    }

    @Override
    public SoulOfEmancipation copy() {
        return new SoulOfEmancipation(this);
    }
}

class SoulOfEmancipationEffect extends OneShotEffect {

    SoulOfEmancipationEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to three other target nonland permanents. For each of those permanents, " +
                "its controller creates a 3/3 white Angel creature token with flying";
    }

    private SoulOfEmancipationEffect(final SoulOfEmancipationEffect effect) {
        super(effect);
    }

    @Override
    public SoulOfEmancipationEffect copy() {
        return new SoulOfEmancipationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        Map<UUID, Integer> playerMap = permanents
                .stream()
                .collect(Collectors.toMap(
                        Controllable::getControllerId,
                        x -> 1,
                        Integer::sum
                ));
        for (Permanent permanent : permanents) {
            permanent.destroy(source, game);
        }
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            new Angel33Token().putOntoBattlefield(entry.getValue(), game, source, entry.getKey());
        }
        return true;
    }
}
