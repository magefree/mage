package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class TheGitrogMonster extends CardImpl {

    public TheGitrogMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // At the beginning of your upkeep, sacrifice The Gitrog Monster unless you sacrifice a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(
                new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent("a land"), true))), TargetController.YOU, false));

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // Whenever one or more land cards are put into your graveyard from anywhere, draw a card.
        this.addAbility(new TheGitrogMonsterTriggeredAbility());
    }

    private TheGitrogMonster(final TheGitrogMonster card) {
        super(card);
    }

    @Override
    public TheGitrogMonster copy() {
        return new TheGitrogMonster(this);
    }
}

class TheGitrogMonsterTriggeredAbility extends TriggeredAbilityImpl {

    public TheGitrogMonsterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public TheGitrogMonsterTriggeredAbility(final TheGitrogMonsterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.GRAVEYARD == zEvent.getToZone()
                && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {
                    UUID cardOwnerId = card.getOwnerId();
                    if (cardOwnerId != null
                            && card.isOwnedBy(getControllerId())
                            && card.isLand(game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TheGitrogMonsterTriggeredAbility copy() {
        return new TheGitrogMonsterTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere, draw a card.";
    }
}
