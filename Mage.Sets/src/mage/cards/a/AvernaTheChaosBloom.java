package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvernaTheChaosBloom extends CardImpl {

    public AvernaTheChaosBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // As you cascade, you may put a land card from among the exiled cards onto the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new AvernaTheChaosBloomReplacementEffect()));
    }

    private AvernaTheChaosBloom(final AvernaTheChaosBloom card) {
        super(card);
    }

    @Override
    public AvernaTheChaosBloom copy() {
        return new AvernaTheChaosBloom(this);
    }
}

class AvernaTheChaosBloomReplacementEffect extends ReplacementEffectImpl {

    AvernaTheChaosBloomReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As you cascade, you may put a land card from among the exiled cards onto the battlefield tapped.";
    }

    private AvernaTheChaosBloomReplacementEffect(final AvernaTheChaosBloomReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AvernaTheChaosBloomReplacementEffect copy() {
        return new AvernaTheChaosBloomReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CASCADE_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
