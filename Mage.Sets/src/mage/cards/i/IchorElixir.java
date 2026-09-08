package mage.cards.i;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.RollDieType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.RollDiceEvent;

import java.util.UUID;

public final class IchorElixir extends CardImpl {

    public IchorElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // If you would roll one or more planar dice, instead roll that many planar dice plus one and ignore one.
        this.addAbility(new SimpleStaticAbility(new IchorElixirEffect()));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private IchorElixir(final IchorElixir card) {
        super(card);
    }

    @Override
    public IchorElixir copy() {
        return new IchorElixir(this);
    }
}

class IchorElixirEffect extends ReplacementEffectImpl {
    IchorElixirEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "if you would roll one or more planar dice, instead roll that many planar dice plus one and ignore one";
    }

    private IchorElixirEffect(final IchorElixirEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((RollDiceEvent) event).incAmount(1);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE
                && ((RollDiceEvent) event).getRollDieType() == RollDieType.PLANAR;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public IchorElixirEffect copy() {
        return new IchorElixirEffect(this);
    }
}
