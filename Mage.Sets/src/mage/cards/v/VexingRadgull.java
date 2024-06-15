package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VexingRadgull extends CardImpl {

    public VexingRadgull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Vexing Radgull deals combat damage to a player, that player gets two rad counters if they don't have any rad counters. Otherwise, proliferate.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new VexingRadgullEffect(), false, true));
    }

    private VexingRadgull(final VexingRadgull card) {
        super(card);
    }

    @Override
    public VexingRadgull copy() {
        return new VexingRadgull(this);
    }
}

class VexingRadgullEffect extends OneShotEffect {

    VexingRadgullEffect() {
        super(Outcome.Benefit);
        staticText = "that player gets two rad counters if they don't have any rad counters. Otherwise, proliferate." +
                " <i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
    }

    private VexingRadgullEffect(final VexingRadgullEffect effect) {
        super(effect);
    }

    @Override
    public VexingRadgullEffect copy() {
        return new VexingRadgullEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        if (player.getCountersCount(CounterType.RAD) == 0) {
            return new AddCountersTargetEffect(CounterType.RAD.createInstance(2))
                    .setTargetPointer(getTargetPointer().copy())
                    .apply(game, source);
        } else {
            return new ProliferateEffect().apply(game, source);
        }
    }

}
