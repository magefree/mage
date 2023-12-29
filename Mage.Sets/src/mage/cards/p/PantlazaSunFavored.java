package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.hint.StaticHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jimga150
 */
public final class PantlazaSunFavored extends CardImpl {

    public PantlazaSunFavored(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Pantlaza, Sun-Favored or another Dinosaur enters the battlefield under your control,
        // you may discover X, where X is that creature's toughness. Do this only once each turn.
        this.addAbility(new PantlazaSunFavoredTriggeredAbility());
    }

    private PantlazaSunFavored(final PantlazaSunFavored card) {
        super(card);
    }

    @Override
    public PantlazaSunFavored copy() {
        return new PantlazaSunFavored(this);
    }
}

class PantlazaSunFavoredTriggeredAbility extends TriggeredAbilityImpl {

    PantlazaSunFavoredTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        setTriggersOnceEachTurn(true);
    }

    private PantlazaSunFavoredTriggeredAbility(final PantlazaSunFavoredTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PantlazaSunFavoredTriggeredAbility copy() {
        return new PantlazaSunFavoredTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }

        Permanent permanent = game.getPermanent(event.getTargetId());

        if (!permanent.hasSubtype(SubType.DINOSAUR, game)){
            return false;
        }

        int toughness = permanent.getToughness().getModifiedBaseValue();

        this.getEffects().clear();
        this.getEffects().add(new DiscoverEffect(toughness));
        this.getHints().clear();
        this.getHints().add(new StaticHint("Discover amount: " + toughness));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever Pantlaza, Sun-Favored or another Dinosaur enters the battlefield under your control, " +
                "you may discover X, where X is that creature's toughness. Do this only once each turn.";
    }
}
