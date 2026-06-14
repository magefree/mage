package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SpiderWomanSecretAgent extends CardImpl {

    public SpiderWomanSecretAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Spider-Woman enters, tap target creature an opponent controls. That creature can't become untapped for as long as you control Spider-Woman.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new SpiderWomanSecretAgentEffect());
        this.addAbility(ability);
    }

    private SpiderWomanSecretAgent(final SpiderWomanSecretAgent card) {
        super(card);
    }

    @Override
    public SpiderWomanSecretAgent copy() {
        return new SpiderWomanSecretAgent(this);
    }
}

class SpiderWomanSecretAgentEffect extends ReplacementEffectImpl {

    SpiderWomanSecretAgentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "That creature can't become untapped for as long as you control {this}";
    }

    private SpiderWomanSecretAgentEffect(final SpiderWomanSecretAgentEffect effect) {
        super(effect);
    }

    @Override
    public SpiderWomanSecretAgentEffect copy() {
        return new SpiderWomanSecretAgentEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return game.getPermanent(event.getTargetId()) != null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }
}
