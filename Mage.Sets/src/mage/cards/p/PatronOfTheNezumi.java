package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;


/**
 * @author LevelX2
 */
public final class PatronOfTheNezumi extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.RAT, "Rat");

    public PatronOfTheNezumi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Rat offering (You may cast this card any time you could cast an instant by sacrificing a Rat and paying the difference in mana costs between this and the sacrificed Rat. Mana cost includes color.)
        this.addAbility(new OfferingAbility(filter));

        // Whenever a permanent is put into an opponent's graveyard, that player loses 1 life.
        this.addAbility(new PatronOfTheNezumiTriggeredAbility(new LoseLifeTargetEffect(1)));

    }

    private PatronOfTheNezumi(final PatronOfTheNezumi card) {
        super(card);
    }

    @Override
    public PatronOfTheNezumi copy() {
        return new PatronOfTheNezumi(this);
    }
}

class PatronOfTheNezumiTriggeredAbility extends TriggeredAbilityImpl {

    public PatronOfTheNezumiTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public PatronOfTheNezumiTriggeredAbility(final PatronOfTheNezumiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
            if (permanent != null && game.getOpponents(controllerId).contains(permanent.getOwnerId())) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getOwnerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a permanent is put into an opponent's graveyard, that player loses 1 life.";
    }

    @Override
    public PatronOfTheNezumiTriggeredAbility copy() {
        return new PatronOfTheNezumiTriggeredAbility(this);
    }
}
