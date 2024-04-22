package mage.cards.i;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class InfernalDarkness extends CardImpl {

    public InfernalDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Cumulative upkeep-Pay {B} and 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new CompositeCost(
                new ManaCostsImpl<>("{B}"), new PayLifeCost(1), "pay {B} and 1 life"
        )));

        // If a land is tapped for mana, it produces {B} instead of any other type.
        this.addAbility(new SimpleStaticAbility(new InfernalDarknessReplacementEffect()));
    }

    private InfernalDarkness(final InfernalDarkness card) {
        super(card);
    }

    @Override
    public InfernalDarkness copy() {
        return new InfernalDarkness(this);
    }
}

class InfernalDarknessReplacementEffect extends ReplacementEffectImpl {

    InfernalDarknessReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for mana, it produces {B} instead of any other type";
    }

    private InfernalDarknessReplacementEffect(final InfernalDarknessReplacementEffect effect) {
        super(effect);
    }

    @Override
    public InfernalDarknessReplacementEffect copy() {
        return new InfernalDarknessReplacementEffect(this);
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
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((TappedForManaEvent) event).getPermanent();
        return permanent != null && permanent.isLand(game);
    }
}
