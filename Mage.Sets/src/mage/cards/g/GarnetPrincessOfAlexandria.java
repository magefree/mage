package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarnetPrincessOfAlexandria extends CardImpl {

    public GarnetPrincessOfAlexandria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Garnet attacks, you may remove a lore counter from each of any number of Sagas you control. Put a +1/+1 counter on Garnet for each lore counter removed this way.
        this.addAbility(new AttacksTriggeredAbility(new GarnetPrincessOfAlexandriaEffect()));
    }

    private GarnetPrincessOfAlexandria(final GarnetPrincessOfAlexandria card) {
        super(card);
    }

    @Override
    public GarnetPrincessOfAlexandria copy() {
        return new GarnetPrincessOfAlexandria(this);
    }
}

class GarnetPrincessOfAlexandriaEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA, "Sagas you control");

    static {
        filter.add(CounterType.LORE.getPredicate());
    }

    GarnetPrincessOfAlexandriaEffect() {
        super(Outcome.Benefit);
        staticText = "you may remove a lore counter from each of any number of Sagas you control. " +
                "Put a +1/+1 counter on {this} for each lore counter removed this way";
    }

    private GarnetPrincessOfAlexandriaEffect(final GarnetPrincessOfAlexandriaEffect effect) {
        super(effect);
    }

    @Override
    public GarnetPrincessOfAlexandriaEffect copy() {
        return new GarnetPrincessOfAlexandriaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        target.withChooseHint("to remove lore counters from");
        player.choose(outcome, target, source, game);
        int count = target.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .mapToInt(permanent -> permanent.removeCounters(CounterType.LORE.createInstance(), source, game))
                .sum();
        if (count < 1) {
            return false;
        }
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(count), source, game));
        return true;
    }
}
