
package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class MaintenanceHangar extends CardImpl {

    private static final FilterCreaturePermanent filterPermanent = new FilterCreaturePermanent("Starship creatures");

    static {
        filterPermanent.add(SubType.STARSHIP.getPredicate());
    }

    public MaintenanceHangar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // At the beginning of your upkeep, remove an additional repair counter from each card in your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterMaintenanceHangarEffect(), TargetController.YOU, false));

        // Starship creatures you control and starship creatures in your graveyard have Repair 6.
        Effect effect = new GainAbilityControlledEffect(new RepairAbility(6), Duration.WhileOnBattlefield, filterPermanent);
        effect.setText("Starship creatures you control");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new MaintenanceHangarEffect());
        this.addAbility(ability);
    }

    private MaintenanceHangar(final MaintenanceHangar card) {
        super(card);
    }

    @Override
    public MaintenanceHangar copy() {
        return new MaintenanceHangar(this);
    }
}

class RemoveCounterMaintenanceHangarEffect extends OneShotEffect {

    public RemoveCounterMaintenanceHangarEffect() {
        super(Outcome.Detriment);
        staticText = "remove an additional repair counter from each card in your graveyard";
    }

    public RemoveCounterMaintenanceHangarEffect(final RemoveCounterMaintenanceHangarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (card.getCounters(game).getCount(CounterType.REPAIR) > 0) {
                    card.removeCounters(CounterType.REPAIR.getName(), 1, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RemoveCounterMaintenanceHangarEffect copy() {
        return new RemoveCounterMaintenanceHangarEffect(this);
    }
}

class MaintenanceHangarEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filterCard = new FilterCreatureCard("Starship creatures");

    static {
        filterCard.add(SubType.STARSHIP.getPredicate());
    }

    public MaintenanceHangarEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "and starship creatures in your graveyard have Repair 6";
    }

    public MaintenanceHangarEffect(final MaintenanceHangarEffect effect) {
        super(effect);
    }

    @Override
    public MaintenanceHangarEffect copy() {
        return new MaintenanceHangarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cards = controller.getGraveyard().getCards(filterCard, game);
            cards.stream().forEach((card) -> {
                game.getState().addOtherAbility(card, new RepairAbility(6));
            });
            return true;
        }
        return false;
    }
}
