package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Stabilizer extends CardImpl {

    public Stabilizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Players can't cycle cards.
        this.addAbility(new SimpleStaticAbility(new StabilizerEffect()));
    }

    private Stabilizer(final Stabilizer card) {
        super(card);
    }

    @Override
    public Stabilizer copy() {
        return new Stabilizer(this);
    }
}

class StabilizerEffect extends ContinuousRuleModifyingEffectImpl {

    StabilizerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't cycle cards";
    }

    private StabilizerEffect(final StabilizerEffect effect) {
        super(effect);
    }

    @Override
    public StabilizerEffect copy() {
        return new StabilizerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject == null) {
            return null;
        }
        return "You can't cycle cards (" + mageObject.getIdName() + ").";
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATE_ABILITY) {
            return false;
        }
        Ability ability = game.getAbility(event.getTargetId(), event.getSourceId()).orElse(null);
        return ability instanceof LoyaltyAbility;
    }
}
