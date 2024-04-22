package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeriadocBrandybuck extends CardImpl {

    public MeriadocBrandybuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever one or more Halflings you control attack a player, create a Food token.
        this.addAbility(new MeriadocBrandybuckTriggeredAbility());
    }

    private MeriadocBrandybuck(final MeriadocBrandybuck card) {
        super(card);
    }

    @Override
    public MeriadocBrandybuck copy() {
        return new MeriadocBrandybuck(this);
    }
}

class MeriadocBrandybuckTriggeredAbility extends TriggeredAbilityImpl {

    MeriadocBrandybuckTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new FoodToken()));
        setTriggerPhrase("Whenever one or more Halflings you control attack a player, ");
    }

    private MeriadocBrandybuckTriggeredAbility(final MeriadocBrandybuckTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MeriadocBrandybuckTriggeredAbility copy() {
        return new MeriadocBrandybuckTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && game.getPlayer(event.getTargetId()) != null
                && ((DefenderAttackedEvent) event)
                .getAttackers(game)
                .stream()
                .anyMatch(permanent -> permanent.hasSubtype(SubType.HALFLING, game));
    }
}
