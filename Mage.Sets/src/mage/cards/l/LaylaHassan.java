package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreDamagePlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class LaylaHassan extends CardImpl {

    private static final FilterCard filter = new FilterCard("historic card from your graveyard");
    static {
        filter.add(HistoricPredicate.instance);
    }

    public LaylaHassan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Layla Hassan enters the battlefield and whenever one or more Assassins you control deal combat damage to a player, return target historic card from your graveyard to your hand.
        Ability ability = new LaylaHassanTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private LaylaHassan(final LaylaHassan card) {
        super(card);
    }

    @Override
    public LaylaHassan copy() {
        return new LaylaHassan(this);
    }
}

class LaylaHassanTriggeredAbility extends OneOrMoreDamagePlayerTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Assassins");

    static {
        filter.add(SubType.ASSASSIN.getPredicate());
    }

    public LaylaHassanTriggeredAbility(Effect effect) {
        super(effect, filter, true, true);
    }

    private LaylaHassanTriggeredAbility(final LaylaHassanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD 
            || event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                && event.getTargetId().equals(getSourceId())) {
            return true;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER) {
            return super.checkTrigger(event, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield and whenever one or more Assassins you control deal combat damage to a player, return target historic card from your graveyard to your hand.";
    }

    @Override
    public LaylaHassanTriggeredAbility copy() {
        return new LaylaHassanTriggeredAbility(this);
    }
}
