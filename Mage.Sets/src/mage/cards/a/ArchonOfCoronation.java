package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfCoronation extends CardImpl {

    public ArchonOfCoronation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Archon of Coronation enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // As long as you're the monarch, damage doesn't cause you to lose life.
        this.addAbility(new SimpleStaticAbility(new ArchonOfCoronationEffect()));
    }

    private ArchonOfCoronation(final ArchonOfCoronation card) {
        super(card);
    }

    @Override
    public ArchonOfCoronation copy() {
        return new ArchonOfCoronation(this);
    }
}

class ArchonOfCoronationEffect extends ReplacementEffectImpl {

    ArchonOfCoronationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you're the monarch, damage doesn't cause you to lose life. " +
                "<i>(When a creature deals combat damage to you, its controller still becomes the monarch.)</i>";
    }

    private ArchonOfCoronationEffect(final ArchonOfCoronationEffect effect) {
        super(effect);
    }

    @Override
    public ArchonOfCoronationEffect copy() {
        return new ArchonOfCoronationEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && source.isControlledBy(game.getMonarchId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(0);
        return false;
    }
}
