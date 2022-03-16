package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public final class AngelOfDeliverance extends CardImpl {

    public AngelOfDeliverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // <i>Delirium</i> &mdash; Whenever Angel of Deliverance deals damage, if there are four or more card types among cards in your graveyard,
        // exile target creature an opponent controls.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AngelOfDeliveranceDealsDamageTriggeredAbility(),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; Whenever {this} deals damage, if there are four or more card types among cards in your graveyard, exile target creature an opponent controls"
        );
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private AngelOfDeliverance(final AngelOfDeliverance card) {
        super(card);
    }

    @Override
    public AngelOfDeliverance copy() {
        return new AngelOfDeliverance(this);
    }
}

class AngelOfDeliveranceDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public AngelOfDeliveranceDealsDamageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), false);
    }

    public AngelOfDeliveranceDealsDamageTriggeredAbility(final AngelOfDeliveranceDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AngelOfDeliveranceDealsDamageTriggeredAbility copy() {
        return new AngelOfDeliveranceDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }
}
