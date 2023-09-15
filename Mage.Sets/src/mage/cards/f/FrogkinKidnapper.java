
package mage.cards.f;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FrogkinKidnapper extends CardImpl {

    public FrogkinKidnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Frogkin Kidnapper enters the battlefield, target opponent reveals their hand. Choose a nonland card from it. Ransom that card. (Exile it. Its owner may pay {3} at any time to return it to their hand.)
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new FrogkinKidnapperEffect()
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private FrogkinKidnapper(final FrogkinKidnapper card) {
        super(card);
    }

    @Override
    public FrogkinKidnapper copy() {
        return new FrogkinKidnapper(this);
    }
}

class FrogkinKidnapperEffect extends OneShotEffect {

    FrogkinKidnapperEffect() {
        super(Outcome.Exile);
        this.staticText = "target opponent reveals their hand. Choose a nonland card from it. "
                + "Ransom that card. <i>(Exile it. Its owner may pay {3} at any time to return it to their hand.)</i>";
    }

    private FrogkinKidnapperEffect(final FrogkinKidnapperEffect effect) {
        super(effect);
    }

    @Override
    public FrogkinKidnapperEffect copy() {
        return new FrogkinKidnapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent == null || controller == null) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target = new TargetCard(1, Zone.HAND, StaticFilters.FILTER_CARD_A_NON_LAND);
        Cards toRansom = new CardsImpl();
        if (controller.chooseTarget(outcome, opponent.getHand(), target, source, game)) {
            toRansom.addAll(target.getTargets());

            String exileName = "Ransomed (owned by " + opponent.getName() + ")";
            UUID exileId = CardUtil.getExileZoneId("Ransom|" + opponent.getId(), game);

            for (Card card : toRansom.getCards(game)) {
                card.moveToExile(exileId, exileName, source, game);

                MageObjectReference mor = new MageObjectReference(card, game);
                SpecialAction specialAction = new FrogkinKidnapperSpecialAction(card.getIdName());
                specialAction.getEffects().setTargetPointer(new FixedTarget(mor));
                new CreateSpecialActionEffect(specialAction, opponent.getId()).apply(game, source);

                // Create a hidden delayed triggered ability to remove the special action when the card leaves the zone.
                new CreateDelayedTriggeredAbilityEffect(
                        new FrogkinKidnapperTriggeredAbility(mor, specialAction.getId()),
                        false
                ).apply(game, source);
            }
        }
        return true;
    }
}

class FrogkinKidnapperSpecialAction extends SpecialAction {

    FrogkinKidnapperSpecialAction(String idName) {
        super();
        this.addCost(new GenericManaCost(3));
        this.addEffect(new ReturnToHandTargetEffect().setText("return " + idName + " to your hand. (Ransomed)"));
    }

    private FrogkinKidnapperSpecialAction(final FrogkinKidnapperSpecialAction ability) {
        super(ability);
    }

    @Override
    public FrogkinKidnapperSpecialAction copy() {
        return new FrogkinKidnapperSpecialAction(this);
    }

}

class FrogkinKidnapperTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    FrogkinKidnapperTriggeredAbility(MageObjectReference mor, UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
        this.mor = mor;
    }

    private FrogkinKidnapperTriggeredAbility(final FrogkinKidnapperTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public FrogkinKidnapperTriggeredAbility copy() {
        return new FrogkinKidnapperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getFromZone() == Zone.EXILED
                && zEvent.getTargetId().equals(mor.getSourceId());
    }
}
