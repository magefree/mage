package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class CarthTheLion extends CardImpl {

    public CarthTheLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Carth the Lion enters the battlefield, or a planeswalker you control dies, look at the top seven cards of your library.
        // You may reveal a planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new CarthTheLionTriggeredAbility());

        // Planeswalkers' loyalty abilities you activate cost an additonal {+1} to activate.
        this.addAbility(new SimpleStaticAbility(new CarthTheLionLoyaltyCostEffect()));
    }

    private CarthTheLion(final CarthTheLion card) {
        super(card);
    }

    @Override
    public CarthTheLion copy() {
        return new CarthTheLion(this);
    }
}

class CarthTheLionTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPlaneswalkerCard filter = new FilterPlaneswalkerCard("a planeswalker card");

    public CarthTheLionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LookLibraryAndPickControllerEffect(
                7, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM));
        setTriggerPhrase("Whenever {this} enters the battlefield or a planeswalker you control dies, ");
    }

    private CarthTheLionTriggeredAbility(final CarthTheLionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarthTheLionTriggeredAbility copy() {
        return new CarthTheLionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getPlayerId().equals(getControllerId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTargetId());
                return permanent != null && permanent.isPlaneswalker();
            }
        }
        return false;
    }
}

class CarthTheLionLoyaltyCostEffect extends CostModificationEffectImpl {

    public CarthTheLionLoyaltyCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Planeswalkers' loyalty abilities you activate cost an additional +1 to activate";
    }

    private CarthTheLionLoyaltyCostEffect(final CarthTheLionLoyaltyCostEffect effect) {
        super(effect);
    }

    @Override
    public CarthTheLionLoyaltyCostEffect copy() {
        return new CarthTheLionLoyaltyCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (abilityToModify instanceof LoyaltyAbility) {
            ((LoyaltyAbility) abilityToModify).increaseLoyaltyCost(1);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof LoyaltyAbility && abilityToModify.getControllerId().equals(source.getControllerId());
    }
}
