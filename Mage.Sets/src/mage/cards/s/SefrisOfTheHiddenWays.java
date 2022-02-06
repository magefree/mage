package mage.cards.s;

import java.util.Set;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CompletedDungeonTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class SefrisOfTheHiddenWays extends CardImpl {

    public SefrisOfTheHiddenWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever one or more creature cards are put into your graveyard from anywhere, venture into the dungeon. This ability triggers only once each turn.
        this.addAbility(new SefrisOfTheHiddenWaysTriggeredAbility(
                new VentureIntoTheDungeonEffect().setText("")
        ).setTriggersOnce(true));

        // Create Undead — Whenever you complete a dungeon, return target creature card from your graveyard to the battlefield.
        Ability ability = new CompletedDungeonTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability.withFlavorWord("Create Undead"));
    }

    private SefrisOfTheHiddenWays(final SefrisOfTheHiddenWays card) {
        super(card);
    }

    @Override
    public SefrisOfTheHiddenWays copy() {
        return new SefrisOfTheHiddenWays(this);
    }
}

class SefrisOfTheHiddenWaysTriggeredAbility extends TriggeredAbilityImpl {

    public SefrisOfTheHiddenWaysTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, false);
    }

    public SefrisOfTheHiddenWaysTriggeredAbility(final SefrisOfTheHiddenWaysTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SefrisOfTheHiddenWaysTriggeredAbility copy() {
        return new SefrisOfTheHiddenWaysTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Boolean applies = false;
        /*
        Sefris of the Hidden Ways must be on the battlefield for its first ability to trigger. 
        It does not trigger when Sefris goes to the graveyard from the battlefield, even if other 
        creature cards also went to the graveyard at the same time.
         */
        Permanent sourceCard = game.getPermanent(sourceId);
        if (((ZoneChangeGroupEvent) event).getToZone() != Zone.GRAVEYARD
                || sourceCard == null) {
            return false;
        }
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        Set<Card> cards = zEvent.getCards();
        for (Card card : cards) {
            if (card.isCreature(game)
                    && (Card) sourceCard != card // 603.6c, 603.10a, and 603.10.
                    && !card.isCopy()
                    && card.isOwnedBy(controllerId)) {
                applies = true;
            }
        }
        return applies;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever one or more creature cards are put into your graveyard from anywhere, venture into the dungeon.";
    }
}
