package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class BelltowerSphinx extends CardImpl {

    public BelltowerSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a source deals damage to Belltower Sphinx, that source's controller puts that many cards from the top of their library into their graveyard.
        this.addAbility(new BelltowerSphinxEffect());
    }

    private BelltowerSphinx(final BelltowerSphinx card) {
        super(card);
    }

    @Override
    public BelltowerSphinx copy() {
        return new BelltowerSphinx(this);
    }
}

class BelltowerSphinxEffect extends TriggeredAbilityImpl {

    public BelltowerSphinxEffect() {
        super(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(0));
    }

    public BelltowerSphinxEffect(BelltowerSphinxEffect effect) {
        super(effect);
    }

    @Override
    public BelltowerSphinxEffect copy() {
        return new BelltowerSphinxEffect(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            UUID controller = game.getControllerId(event.getSourceId());
            if (controller != null) {
                Player player = game.getPlayer(controller);
                if (player != null) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(player.getId()));
                    ((PutLibraryIntoGraveTargetEffect) getEffects().get(0)).setAmount(StaticValue.get(event.getAmount()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, that source's controller mills that many cards.";
    }
}
