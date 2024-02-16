package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagnanimousMagistrate extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 0));
    }

    public MagnanimousMagistrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Magnanimous Magistrate enters the battlefield with five reprieve counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.REPRIEVE.createInstance(5)),
                "with five reprieve counters on it"
        ));

        // Whenever another nontoken creature you control dies, if its mana value was 1 or greater, you may remove that many reprieve counters from Magnanimous Magistrate. If you do, return that card to the battlefield under its owner's control.
        this.addAbility(new DiesCreatureTriggeredAbility(new MagnanimousMagistrateEffect(), false, filter));
    }

    private MagnanimousMagistrate(final MagnanimousMagistrate card) {
        super(card);
    }

    @Override
    public MagnanimousMagistrate copy() {
        return new MagnanimousMagistrate(this);
    }
}

class MagnanimousMagistrateEffect extends OneShotEffect {

    MagnanimousMagistrateEffect() {
        super(Outcome.Benefit);
        staticText = "if its mana value was 1 or greater, you may remove that many reprieve counters " +
                "from {this}. If you do, return that card to the battlefield under its owner's control";
    }

    private MagnanimousMagistrateEffect(final MagnanimousMagistrateEffect effect) {
        super(effect);
    }

    @Override
    public MagnanimousMagistrateEffect copy() {
        return new MagnanimousMagistrateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = (Permanent) getValue("creatureDied");
        if (player == null || sourcePermanent == null || creature == null
                || sourcePermanent.getCounters(game).getCount(CounterType.REPRIEVE) < creature.getManaValue()
                || game.getState().getZoneChangeCounter(creature.getId()) != creature.getZoneChangeCounter(game) + 1
                || !player.chooseUse(outcome, "Remove " + creature.getManaValue() +
                " reprieve counters from " + sourcePermanent.getName() + '?', source, game)) {
            return false;
        }
        sourcePermanent.removeCounters(CounterType.REPRIEVE.createInstance(creature.getManaValue()), source, game);
        player.moveCards(
                game.getCard(creature.getId()), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
        return true;
    }
}
