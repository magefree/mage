package mage.cards.e;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarsAwakening extends CardImpl {

    public EdgarsAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // When you discard Edgar's Awakening, you may pay {B}. When you do, return target creature card from your graveyard to your hand.
        this.addAbility(new EdgarsAwakeningTriggeredAbility());
    }

    private EdgarsAwakening(final EdgarsAwakening card) {
        super(card);
    }

    @Override
    public EdgarsAwakening copy() {
        return new EdgarsAwakening(this);
    }
}

class EdgarsAwakeningTriggeredAbility extends TriggeredAbilityImpl {

    private static final ReflexiveTriggeredAbility makeAbility() {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), false,
                "return target creature card from your graveyard to your hand"
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        return ability;
    }

    EdgarsAwakeningTriggeredAbility() {
        super(Zone.ALL, new DoWhenCostPaid(makeAbility(), new ManaCostsImpl<>("{B}"), "Pay {B}?"));
    }

    private EdgarsAwakeningTriggeredAbility(final EdgarsAwakeningTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EdgarsAwakeningTriggeredAbility copy() {
        return new EdgarsAwakeningTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return this.getSourceId().equals(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "When you discard {this}, you may pay {B}. " +
                "When you do, return target creature card from your graveyard to your hand.";
    }
}
