
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class PaleMoon extends CardImpl {

    public PaleMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Until end of turn, if a player taps a nonbasic land for mana, it produces colorless mana instead of any other type.
        this.getSpellAbility().addEffect(new PaleMoonReplacementEffect());
    }

    public PaleMoon(final PaleMoon card) {
        super(card);
    }

    @Override
    public PaleMoon copy() {
        return new PaleMoon(this);
    }
}

class PaleMoonReplacementEffect extends ReplacementEffectImpl {

    private static final FilterLandPermanent filter = FilterLandPermanent.nonbasicLands();

    PaleMoonReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "Until end of turn, if a player taps a nonbasic land for mana, it produces colorless mana instead of any other type";
    }

    PaleMoonReplacementEffect(final PaleMoonReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PaleMoonReplacementEffect copy() {
        return new PaleMoonReplacementEffect(this);
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
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (mageObject != null && mageObject.isLand()) {
            Permanent land = game.getPermanent(event.getSourceId());
            return land != null && filter.match(land, game);
        }
        return false;
    }
}
