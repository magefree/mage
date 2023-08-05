package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author weirddan455
 */
public final class VegaTheWatcher extends CardImpl {

    public VegaTheWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell from anywhere other than your hand, draw a card.
        this.addAbility(new VegaTheWatcherTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private VegaTheWatcher(final VegaTheWatcher card) {
        super(card);
    }

    @Override
    public VegaTheWatcher copy() {
        return new VegaTheWatcher(this);
    }
}

class VegaTheWatcherTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public VegaTheWatcherTriggeredAbility(Effect effect, boolean optional) {
        super(effect, optional);
        this.rule = "Whenever you cast a spell from anywhere other than your hand, draw a card.";
    }

    public VegaTheWatcherTriggeredAbility(final VegaTheWatcherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VegaTheWatcherTriggeredAbility copy() {
        return new VegaTheWatcherTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getZone() != Zone.HAND && super.checkTrigger(event, game);
    }
}
