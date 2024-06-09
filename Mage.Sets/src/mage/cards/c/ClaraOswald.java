package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClaraOswald extends CardImpl {

    public ClaraOswald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Impossible Girl -- If Clara Oswald is your commander, choose a color before the game begins. Clara Oswald is the chosen color.
        this.addAbility(new CommanderChooseColorAbility().withFlavorWord("Impossible Girl"));

        // If a triggered ability of a Doctor you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new ClaraOswaldEffect()));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private ClaraOswald(final ClaraOswald card) {
        super(card);
    }

    @Override
    public ClaraOswald copy() {
        return new ClaraOswald(this);
    }
}

class ClaraOswaldEffect extends ReplacementEffectImpl {

    ClaraOswaldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of a Doctor you control triggers, " +
                "that ability triggers an additional time";
    }

    private ClaraOswaldEffect(final ClaraOswaldEffect effect) {
        super(effect);
    }

    @Override
    public ClaraOswaldEffect copy() {
        return new ClaraOswaldEffect(this);
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
                && permanent.hasSubtype(SubType.DOCTOR, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}
