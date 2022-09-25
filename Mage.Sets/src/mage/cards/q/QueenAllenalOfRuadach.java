package mage.cards.q;

import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author weirddan455
 */
public final class QueenAllenalOfRuadach extends CardImpl {

    private static final PermanentsOnBattlefieldCount count
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES);

    public QueenAllenalOfRuadach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Queen Allenal of Ruadach's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(count, Duration.EndOfGame)
        ));

        // If one or more creature tokens would be created under your control, those tokens plus a 1/1 white Soldier creature token are created instead.
        this.addAbility(new SimpleStaticAbility(new QueenAllenalOfRuadachEffect()));
    }

    private QueenAllenalOfRuadach(final QueenAllenalOfRuadach card) {
        super(card);
    }

    @Override
    public QueenAllenalOfRuadach copy() {
        return new QueenAllenalOfRuadach(this);
    }
}

class QueenAllenalOfRuadachEffect extends ReplacementEffectImpl {

    public QueenAllenalOfRuadachEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more creature tokens would be created under your control, those tokens plus a 1/1 white Soldier creature token are created instead.";
    }

    private QueenAllenalOfRuadachEffect(final QueenAllenalOfRuadachEffect effect) {
        super(effect);
    }

    @Override
    public QueenAllenalOfRuadachEffect copy() {
        return new QueenAllenalOfRuadachEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            for (Map.Entry<Token, Integer> entry : ((CreateTokenEvent) event).getTokens().entrySet()) {
                if (entry.getValue() > 0 && entry.getKey().isCreature(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Map.Entry<Token, Integer> entry : tokens.entrySet()) {
            if (entry.getKey() instanceof SoldierToken) {
                entry.setValue(entry.getValue() + 1);
                return false;
            }
        }
        tokens.put(new SoldierToken(), 1);
        return false;
    }
}
