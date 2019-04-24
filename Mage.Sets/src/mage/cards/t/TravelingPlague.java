
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class TravelingPlague extends CardImpl {

    public TravelingPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of each upkeep, put a plague counter on Traveling Plague.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.PLAGUE.createInstance()), TargetController.ANY, false));

        // Enchanted creature gets -1/-1 for each plague counter on Traveling Plague.
        DynamicValue boostValue = new MultipliedValue(new CountersSourceCount(CounterType.PLAGUE), -1);
        Effect effect = new BoostEnchantedEffect(boostValue, boostValue, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets -1/-1 for each plague counter on {this}");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // When enchanted creature leaves the battlefield, that creature's controller returns Traveling Plague from its owner's graveyard to the battlefield.
        this.addAbility(new TravelingPlagueTriggeredAbility());

    }

    public TravelingPlague(final TravelingPlague card) {
        super(card);
    }

    @Override
    public TravelingPlague copy() {
        return new TravelingPlague(this);
    }
}

class TravelingPlagueTriggeredAbility extends TriggeredAbilityImpl {

    public TravelingPlagueTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TravelingPlagueEffect(), false);
    }

    public TravelingPlagueTriggeredAbility(final TravelingPlagueTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TravelingPlagueTriggeredAbility copy() {
        return new TravelingPlagueTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
            Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            Permanent travelingPlague = game.getPermanentOrLKIBattlefield(sourceId);
            if (enchantedCreature != null
                    && enchantedCreature.getAttachments().contains(travelingPlague.getId())) {
                game.getState().setValue("travelingPlague" + sourceId, enchantedCreature);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature leaves the battlefield, " + super.getRule();
    }
}

class TravelingPlagueEffect extends OneShotEffect {

    public TravelingPlagueEffect() {
        super(Outcome.Detriment);
        staticText = "that creature's controller returns {this} from its owner's graveyard to the battlefield";
    }

    public TravelingPlagueEffect(final TravelingPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card travelingPlague = game.getCard(source.getSourceId());
        Permanent enchantedCreature = (Permanent) game.getState().getValue("travelingPlague" + source.getSourceId());
        if (enchantedCreature != null) {
            Player controllerOfEnchantedCreature = game.getPlayer(enchantedCreature.getControllerId());
            if (travelingPlague != null
                    && game.getState().getZone(travelingPlague.getId()) == Zone.GRAVEYARD // aura must come from the graveyard
                    && controllerOfEnchantedCreature != null) {
                TargetPermanent target = new TargetPermanent(new FilterCreaturePermanent("creature to enchant with " + travelingPlague.getName()));
                target.setNotTarget(true);
                if (controllerOfEnchantedCreature.choose(Outcome.Detriment, target, source.getSourceId(), game)) {
                    Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                    if (!targetPermanent.cantBeAttachedBy(travelingPlague, game)) {
                        game.getState().setValue("attachTo:" + travelingPlague.getId(), targetPermanent);
                        controllerOfEnchantedCreature.moveCards(travelingPlague, Zone.BATTLEFIELD, source, game);
                        return targetPermanent.addAttachment(travelingPlague.getId(), game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TravelingPlagueEffect copy() {
        return new TravelingPlagueEffect(this);
    }
}
