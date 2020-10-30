package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.Cost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author cbt33, Nantuko (Nim Deathmantle)
 */
public final class DecayingSoil extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(Predicates.not(TokenPredicate.instance));
    }

    public DecayingSoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");


        // At the beginning of your upkeep, exile a card from your graveyard.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), TargetController.YOU, false);
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
        ability.addTarget(target);
        this.addAbility(ability);

        // Threshold - As long as seven or more cards are in your graveyard, Decaying Soil has "Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {1}. If you do, return that card to your hand."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(new DecayingSoilTriggeredAbility(new DecayingSoilEffect(), filter)),
                        new CardsInControllerGraveyardCondition(7),
                        "As long as seven or more cards are in your graveyard, {this} has \"Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {1}. If you do, return that card to your hand.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    public DecayingSoil(final DecayingSoil card) {
        super(card);
    }

    @Override
    public DecayingSoil copy() {
        return new DecayingSoil(this);
    }
}

class DecayingSoilTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

    public DecayingSoilTriggeredAbility(Effect effect, FilterCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
    }

    public DecayingSoilTriggeredAbility(DecayingSoilTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DecayingSoilTriggeredAbility copy() {
        return new DecayingSoilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && filter.match(permanent, this.getSourceId(), this.getControllerId(), game)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        return controller != null && controller.getGraveyard().contains(this.getSourceId());
    }


    @Override
    public String getRule() {
        return "Whenever a " + filter.getMessage() + " is put into your graveyard from the battlefield, " + super.getRule();
    }
}


class DecayingSoilEffect extends OneShotEffect {

    private final Cost cost = ManaUtil.createManaCost(1, false);

    public DecayingSoilEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {1}. If you do, return that card to your hand";

    }

    public DecayingSoilEffect(final DecayingSoilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.chooseUse(Outcome.Benefit, " - Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    UUID target = this.getTargetPointer().getFirst(game, source);
                    if (target != null) {
                        Card card = game.getCard(target);
                        // check if it's still in graveyard
                        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public DecayingSoilEffect copy() {
        return new DecayingSoilEffect(this);
    }

}
