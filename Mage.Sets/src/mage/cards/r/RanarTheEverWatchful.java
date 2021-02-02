package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.watchers.common.ForetoldWatcher;

/**
 * @author jeffwadsworth
 */
public final class RanarTheEverWatchful extends CardImpl {

    public RanarTheEverWatchful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // The first card you foretell each turn costs 0 to foretell
        Ability ability = new SimpleStaticAbility(new RanarTheEverWatchfulCostReductionEffect());
        this.addAbility(ability);

        // Whenever you exile one or more cards from your hand and/or permanents from the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new RanarTheEverWatchfulTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));

    }

    private RanarTheEverWatchful(final RanarTheEverWatchful card) {
        super(card);
    }

    @Override
    public RanarTheEverWatchful copy() {
        return new RanarTheEverWatchful(this);
    }
}

class RanarTheEverWatchfulCostReductionEffect extends CostModificationEffectImpl {

    RanarTheEverWatchfulCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.REDUCE_COST);
        staticText = "The first card you foretell each turn costs {0} to foretell";
    }

    private RanarTheEverWatchfulCostReductionEffect(RanarTheEverWatchfulCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public RanarTheEverWatchfulCostReductionEffect copy() {
        return new RanarTheEverWatchfulCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.getManaCostsToPay().clear();
        abilityToModify.getManaCostsToPay().addAll(new ManaCostsImpl<>("{0}"));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        ForetoldWatcher watcher = game.getState().getWatcher(ForetoldWatcher.class);
        return (watcher != null
                && watcher.countNumberForetellThisTurn() == 0
                && abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof ForetellAbility);
    }
}

class RanarTheEverWatchfulTriggeredAbility extends TriggeredAbilityImpl {

    RanarTheEverWatchfulTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    RanarTheEverWatchfulTriggeredAbility(final RanarTheEverWatchfulTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RanarTheEverWatchfulTriggeredAbility copy() {
        return new RanarTheEverWatchfulTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.HAND == zEvent.getFromZone()
                && Zone.EXILED == zEvent.getToZone()
                && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {
                    UUID cardOwnerId = card.getOwnerId();
                    if (cardOwnerId != null
                            && card.isOwnedBy(getControllerId())) {
                        return true;
                    }
                }
            }
        }
        if (zEvent != null
                && Zone.BATTLEFIELD == zEvent.getFromZone()
                && Zone.EXILED == zEvent.getToZone()
                && zEvent.getCards() != null) {
            return true;

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you exile one or more cards from your hand and/or permanents from the battlefield, " + super.getRule();
    }
}
