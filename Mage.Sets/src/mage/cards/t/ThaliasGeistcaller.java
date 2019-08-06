package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThaliasGeistcaller extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SPIRIT, "a Spirit");

    public ThaliasGeistcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you cast a spell from your graveyard, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new ThaliasGeistcallerTriggeredAbility());

        // Sacrifice a Spirit: Thalia's Geistcaller gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private ThaliasGeistcaller(final ThaliasGeistcaller card) {
        super(card);
    }

    @Override
    public ThaliasGeistcaller copy() {
        return new ThaliasGeistcaller(this);
    }
}

class ThaliasGeistcallerTriggeredAbility extends TriggeredAbilityImpl {

    ThaliasGeistcallerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()), false);
    }

    private ThaliasGeistcallerTriggeredAbility(ThaliasGeistcallerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) && event.getZone() == Zone.GRAVEYARD;
    }

    @Override
    public ThaliasGeistcallerTriggeredAbility copy() {
        return new ThaliasGeistcallerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell from your graveyard, " +
                "create a 1/1 white Spirit creature token with flying.";
    }
}
