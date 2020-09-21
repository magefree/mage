package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveShade extends CardImpl {

    public SkyclaveShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {2}{B}
        this.addAbility(new KickerAbility("{2}{B}"));

        // Skyclave Shade can't block.
        this.addAbility(new CantBlockAbility());

        // If Skyclave Shade was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), KickedCondition.instance,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.", ""
        ));

        // Landfall — Whenever a land enters the battlefield under your control, if Skyclave Shade is in your graveyard and it's your turn, you may cast it from your graveyard this turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new LandfallAbility(Zone.GRAVEYARD, new SkyclaveShadeEffect(), false),
                SkyclaveShadeCondition.instance, "<i>Landfall</i> &mdash; Whenever a land " +
                "enters the battlefield under your control, if {this} is in your graveyard and it's your turn, " +
                "you may cast it from your graveyard this turn."
        ));
    }

    private SkyclaveShade(final SkyclaveShade card) {
        super(card);
    }

    @Override
    public SkyclaveShade copy() {
        return new SkyclaveShade(this);
    }
}

enum SkyclaveShadeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getActivePlayerId().equals(source.getControllerId())
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}

class SkyclaveShadeEffect extends AsThoughEffectImpl {

    SkyclaveShadeEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private SkyclaveShadeEffect(final SkyclaveShadeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SkyclaveShadeEffect copy() {
        return new SkyclaveShadeEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!sourceId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        return card != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && source.getSourceObjectZoneChangeCounter() == card.getZoneChangeCounter(game);
    }
}
