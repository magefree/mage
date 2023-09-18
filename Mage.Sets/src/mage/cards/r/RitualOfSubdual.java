package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
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
 * @author L_J
 */
public final class RitualOfSubdual extends CardImpl {

    public RitualOfSubdual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Cumulative upkeep-Pay {2}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{2}")));

        // If a land is tapped for mana, it produces colorless mana instead of any other type.
        this.addAbility(new SimpleStaticAbility(new RitualOfSubdualReplacementEffect()));
    }

    private RitualOfSubdual(final RitualOfSubdual card) {
        super(card);
    }

    @Override
    public RitualOfSubdual copy() {
        return new RitualOfSubdual(this);
    }
}

class RitualOfSubdualReplacementEffect extends ReplacementEffectImpl {

    RitualOfSubdualReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If a land is tapped for mana, it produces colorless mana instead of any other type.";
    }

    private RitualOfSubdualReplacementEffect(final RitualOfSubdualReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RitualOfSubdualReplacementEffect copy() {
        return new RitualOfSubdualReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ManaEvent manaEvent = (ManaEvent) event;
        Mana mana = manaEvent.getMana();
        mana.setToMana(Mana.ColorlessMana(mana.count()));
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
