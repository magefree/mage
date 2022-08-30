package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EnlistAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianOfNewBenalia extends CardImpl {

    public GuardianOfNewBenalia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Enlist
        this.addAbility(new EnlistAbility());

        // Whenever Guardian of New Benalia enlists a creature, scry 2.
        this.addAbility(new GuardianOfNewBenaliaTriggeredAbility());

        // Discard a card: Guardian of New Benalia gains indestructible until end of turn. Tap it.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new DiscardCardCost());
        ability.addEffect(new TapSourceEffect().setText("tap it"));
        this.addAbility(ability);
    }

    private GuardianOfNewBenalia(final GuardianOfNewBenalia card) {
        super(card);
    }

    @Override
    public GuardianOfNewBenalia copy() {
        return new GuardianOfNewBenalia(this);
    }
}

class GuardianOfNewBenaliaTriggeredAbility extends TriggeredAbilityImpl {

    GuardianOfNewBenaliaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ScryEffect(2));
    }

    private GuardianOfNewBenaliaTriggeredAbility(final GuardianOfNewBenaliaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GuardianOfNewBenaliaTriggeredAbility copy() {
        return new GuardianOfNewBenaliaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_ENLISTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getSourceId().equals(event.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} enlists a creature, scry 2.";
    }
}
