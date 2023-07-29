package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RegalBehemoth extends CardImpl {

    public RegalBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Regal Behemoth enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever you tap a land for mana while you're the monarch, add one mana of any color.
        this.addAbility(new RegalBehemothTriggeredManaAbility());
    }

    private RegalBehemoth(final RegalBehemoth card) {
        super(card);
    }

    @Override
    public RegalBehemoth copy() {
        return new RegalBehemoth(this);
    }
}

class RegalBehemothTriggeredManaAbility extends TriggeredManaAbility {

    RegalBehemothTriggeredManaAbility() {
        super(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect());
    }

    private RegalBehemothTriggeredManaAbility(RegalBehemothTriggeredManaAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getMonarchId()) || !isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game);
    }

    @Override
    public RegalBehemothTriggeredManaAbility copy() {
        return new RegalBehemothTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you tap a land for mana while you're the monarch, add an additional one mana of any color.";
    }
}
