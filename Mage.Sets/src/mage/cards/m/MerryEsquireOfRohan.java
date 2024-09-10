package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerryEsquireOfRohan extends CardImpl {

    public MerryEsquireOfRohan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Merry, Esquire of Rohan has first strike as long as it's equipped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                EquippedSourceCondition.instance, "{this} has first strike as long as it's equipped"
        )));

        // Whenever you attack with Merry and another legendary creature, draw a card.
        this.addAbility(new MerryEsquireOfRohanTriggeredAbility());
    }

    private MerryEsquireOfRohan(final MerryEsquireOfRohan card) {
        super(card);
    }

    @Override
    public MerryEsquireOfRohan copy() {
        return new MerryEsquireOfRohan(this);
    }
}

class MerryEsquireOfRohanTriggeredAbility extends TriggeredAbilityImpl {

    MerryEsquireOfRohanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggerPhrase("Whenever you attack with {this} and another legendary creature, ");
    }

    private MerryEsquireOfRohanTriggeredAbility(final MerryEsquireOfRohanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MerryEsquireOfRohanTriggeredAbility copy() {
        return new MerryEsquireOfRohanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getCombat()
                .getAttackers()
                .contains(getSourceId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .filter(uuid -> !getSourceId().equals(uuid))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.isLegendary(game))
                .anyMatch(permanent -> permanent.isCreature(game));
    }
}
