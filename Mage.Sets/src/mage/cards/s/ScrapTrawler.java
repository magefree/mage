package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ScrapTrawler extends CardImpl {

    public ScrapTrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Scrap Trawler or another artifact you control is put into a graveyard from the battlefield,
        // return to your hand target artifact card in your graveyard with lesser converted mana cost.
        Ability ability = new ScrapTrawlerTriggeredAbility();
        ability.addTarget(new TargetCardInYourGraveyard(new FilterArtifactCard("artifact card in your graveyard with lesser mana value")));
        this.addAbility(ability);
    }

    private ScrapTrawler(final ScrapTrawler card) {
        super(card);
    }

    @Override
    public ScrapTrawler copy() {
        return new ScrapTrawler(this);
    }
}

class ScrapTrawlerTriggeredAbility extends TriggeredAbilityImpl {

    public ScrapTrawlerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect());
        getEffects().get(0).setText("return to your hand target artifact card in your graveyard with lesser mana value");
        setTriggerPhrase("Whenever {this} or another artifact you control is put into a graveyard from the battlefield, ");
    }

    public ScrapTrawlerTriggeredAbility(final ScrapTrawlerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScrapTrawlerTriggeredAbility copy() {
        return new ScrapTrawlerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null
                    && permanent.isControlledBy(this.getControllerId())
                    && permanent.isArtifact(game)) {
                FilterCard filter = new FilterArtifactCard("artifact card in your graveyard with mana value less than " + permanent.getManaCost().manaValue());
                filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, permanent.getManaCost().manaValue()));
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
                getTargets().clear();
                addTarget(target);
                return true;
            }
        }
        return false;
    }
}
