package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ConfrontTheAssault extends CardImpl {

    public ConfrontTheAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Cast this spell only if a creature is attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ConfrontTheAssaultRestrictEffect()));

        // Create three 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken("ANA"), 3));
    }

    public ConfrontTheAssault(final ConfrontTheAssault card) {
        super(card);
    }

    @Override
    public ConfrontTheAssault copy() {
        return new ConfrontTheAssault(this);
    }
}

class ConfrontTheAssaultRestrictEffect extends ContinuousRuleModifyingEffectImpl {

    ConfrontTheAssaultRestrictEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast this spell only if a creature is attacking you.";
    }

    ConfrontTheAssaultRestrictEffect(final ConfrontTheAssaultRestrictEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                && event.getTargetId().equals(source.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ConfrontTheAssaultRestrictEffect copy() {
        return new ConfrontTheAssaultRestrictEffect(this);
    }
}