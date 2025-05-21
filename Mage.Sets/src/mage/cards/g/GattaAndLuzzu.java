package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GattaAndLuzzu extends CardImpl {

    public GattaAndLuzzu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Gatta and Luzzu enters, choose target creature you control. If damage would be dealt to that creature this turn, prevent that damage and put that many +1/+1 counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GattaAndLuzzuEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GattaAndLuzzu(final GattaAndLuzzu card) {
        super(card);
    }

    @Override
    public GattaAndLuzzu copy() {
        return new GattaAndLuzzu(this);
    }
}

class GattaAndLuzzuEffect extends PreventionEffectImpl {

    GattaAndLuzzuEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "choose target creature you control. If damage would be dealt " +
                "to that creature this turn, prevent that damage and put that many +1/+1 counters on it";
    }

    private GattaAndLuzzuEffect(final GattaAndLuzzuEffect effect) {
        super(effect);
    }

    @Override
    public GattaAndLuzzuEffect copy() {
        return new GattaAndLuzzuEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game)
                && event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.addCounters(
                        CounterType.P1P1.createInstance(event.getAmount()), source, game
                ));
        return super.replaceEvent(event, source, game);
    }
}
