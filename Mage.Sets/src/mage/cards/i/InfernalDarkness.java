
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class InfernalDarkness extends CardImpl {

    public InfernalDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Cumulative upkeep-Pay {B} and 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new InfernalDarknessCost()));

        // If a land is tapped for mana, it produces {B} instead of any other type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfernalDarknessReplacementEffect()));

    }

    public InfernalDarkness(final InfernalDarkness card) {
        super(card);
    }

    @Override
    public InfernalDarkness copy() {
        return new InfernalDarkness(this);
    }
}

class InfernalDarknessCost extends CostImpl {

    ManaCostsImpl manaCost = new ManaCostsImpl("{B}");
    Cost lifeCost = new PayLifeCost(1);

    public InfernalDarknessCost() {
        this.text = "Pay {B} and 1 life";
    }

    public InfernalDarknessCost(InfernalDarknessCost cost) {
        super(cost);
        this.manaCost = cost.manaCost;
        this.lifeCost = cost.lifeCost;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {

        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }

        paid = false;

        manaCost.clearPaid();
        if (manaCost.pay(ability, game, player.getId(), player.getId(), false)
                && player.canPayLifeCost()
                && player.getLife() >= 1
                && lifeCost.pay(ability, game, player.getId(), player.getId(), false)) {
            paid = true;
        }

        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player != null
                && player.canPayLifeCost()
                && player.getLife() >= 1) {
            return true;
        }
        return false;
    }

    @Override
    public InfernalDarknessCost copy() {
        return new InfernalDarknessCost(this);
    }
}

class InfernalDarknessReplacementEffect extends ReplacementEffectImpl {

    InfernalDarknessReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for mana, it produces {B} instead of any other type";
    }

    InfernalDarknessReplacementEffect(final InfernalDarknessReplacementEffect effect) {
        super(effect);
    }

    @Override
    public InfernalDarknessReplacementEffect copy() {
        return new InfernalDarknessReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.BlackMana(mana.count()));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        return mageObject != null && mageObject.isLand();
    }
}
