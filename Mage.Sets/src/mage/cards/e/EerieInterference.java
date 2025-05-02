package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EerieInterference extends CardImpl {

    public EerieInterference(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent all damage that would be dealt to you and creatures you control by creatures this turn.
        this.getSpellAbility().addEffect(new EerieInterferenceEffect());
    }

    private EerieInterference(final EerieInterference card) {
        super(card);
    }

    @Override
    public EerieInterference copy() {
        return new EerieInterference(this);
    }
}

class EerieInterferenceEffect extends PreventionEffectImpl {

    EerieInterferenceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "prevent all damage that would be dealt to you and creatures you control this turn by creatures";
    }

    private EerieInterferenceEffect(final EerieInterferenceEffect effect) {
        super(effect);
    }

    @Override
    public EerieInterferenceEffect copy() {
        return new EerieInterferenceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        Permanent targetPermanent = game.getPermanent(event.getTargetId());
        boolean isTargetYou = event.getTargetId().equals(source.getControllerId());
        boolean isTargetCreatureYouControl = targetPermanent != null
                && targetPermanent.isCreature(game)
                && targetPermanent.isControlledBy(source.getControllerId());

        Permanent permanentSource = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return (isTargetYou || isTargetCreatureYouControl)
                && permanentSource != null
                && permanentSource.isCreature(game);
    }
}
