package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarmonicProdigy extends CardImpl {

    public HarmonicProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());

        // If an ability of a Shaman or another Wizard you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new HarmonicProdigyEffect()));
    }

    private HarmonicProdigy(final HarmonicProdigy card) {
        super(card);
    }

    @Override
    public HarmonicProdigy copy() {
        return new HarmonicProdigy(this);
    }
}

class HarmonicProdigyEffect extends ReplacementEffectImpl {

    HarmonicProdigyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an ability of a Shaman or another Wizard you control triggers, " +
                "that ability triggers an additional time";
    }

    private HarmonicProdigyEffect(final HarmonicProdigyEffect effect) {
        super(effect);
    }

    @Override
    public HarmonicProdigyEffect copy() {
        return new HarmonicProdigyEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        Permanent permanent = game.getPermanent(((NumberOfTriggersEvent) event).getSourceId());
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && (permanent.hasSubtype(SubType.SHAMAN, game)
                || (permanent.hasSubtype(SubType.WIZARD, game)
                && !permanent.getId().equals(source.getSourceId())));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
