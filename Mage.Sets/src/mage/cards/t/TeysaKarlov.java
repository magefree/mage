package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeysaKarlov extends CardImpl {

    public TeysaKarlov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If a creature dying causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new TeysaKarlovEffect()));

        // Creature tokens you control have vigilance and lifelink.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_TOKENS
                ).setText("creature tokens you control have vigilance")
        );
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(),
                Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURE_TOKENS
        ).setText("and lifelink"));
        this.addAbility(ability);
    }

    private TeysaKarlov(final TeysaKarlov card) {
        super(card);
    }

    @Override
    public TeysaKarlov copy() {
        return new TeysaKarlov(this);
    }
}

class TeysaKarlovEffect extends ReplacementEffectImpl {

    TeysaKarlovEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a creature dying causes a triggered ability of a permanent you control to trigger, " +
                "that ability triggers an additional time.";
    }

    private TeysaKarlovEffect(final TeysaKarlovEffect effect) {
        super(effect);
    }

    @Override
    public TeysaKarlovEffect copy() {
        return new TeysaKarlovEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event instanceof NumberOfTriggersEvent) {
            NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
            if (source.isControlledBy(event.getPlayerId())
                    && game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId()) != null
                    && numberOfTriggersEvent.getSourceEvent() instanceof ZoneChangeEvent) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) numberOfTriggersEvent.getSourceEvent();
                return zEvent != null
                        && zEvent.isDiesEvent()
                        && zEvent.getTarget() != null
                        && zEvent.getTarget().isCreature(game);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
