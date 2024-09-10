package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrahSkyclaveHierophant extends CardImpl {

    public OrahSkyclaveHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Orah, Skyclave Hierophant or another Cleric you control dies, return target Cleric card with lesser converted mana cost from your graveyard to the battlefield.
        this.addAbility(new OrahSkyclaveHierophantTriggeredAbility());
    }

    private OrahSkyclaveHierophant(final OrahSkyclaveHierophant card) {
        super(card);
    }

    @Override
    public OrahSkyclaveHierophant copy() {
        return new OrahSkyclaveHierophant(this);
    }
}

class OrahSkyclaveHierophantTriggeredAbility extends TriggeredAbilityImpl {

    OrahSkyclaveHierophantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect());
    }

    private OrahSkyclaveHierophantTriggeredAbility(final OrahSkyclaveHierophantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()
                ||  !zEvent.getTarget().isControlledBy(getControllerId())
                || (!zEvent.getTarget().hasSubtype(SubType.CLERIC, game)
                    && !zEvent.getTarget().getId().equals(getSourceId()))
                ) {
            return false;
        }
        FilterCard filterCard = new FilterCard(
                "Cleric card with mana value less than " + (zEvent.getTarget().getManaValue())
        );
        filterCard.add(SubType.CLERIC.getPredicate());
        filterCard.add(new ManaValuePredicate(
                ComparisonType.FEWER_THAN, zEvent.getTarget().getManaValue()
        ));
        this.getTargets().clear();
        this.addTarget(new TargetCardInYourGraveyard(filterCard));
        return true;
    }

    @Override
    public OrahSkyclaveHierophantTriggeredAbility copy() {
        return new OrahSkyclaveHierophantTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Cleric you control dies, return target Cleric card " +
                "with lesser mana value from your graveyard to the battlefield.";
    }
}
