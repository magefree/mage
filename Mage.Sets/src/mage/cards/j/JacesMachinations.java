package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class JacesMachinations extends CardImpl {

    public JacesMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control on any player's turn any time you could cast an instant.
        this.getSpellAbility().addEffect(new JacesMachinationsEffect());

        // Empower Jace 8.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(8).concatBy("<br>"));
    }

    private JacesMachinations(final JacesMachinations card) {
        super(card);
    }

    @Override
    public JacesMachinations copy() {
        return new JacesMachinations(this);
    }
}

class JacesMachinationsEffect extends AsThoughEffectImpl {

    JacesMachinationsEffect() {
        super(AsThoughEffectType.ACTIVATE_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may activate loyalty abilities of Jace planeswalkers you control " +
                "on any player's turn any time you could cast an instant";
    }

    private JacesMachinationsEffect(final JacesMachinationsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public JacesMachinationsEffect copy() {
        return new JacesMachinationsEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!affectedAbility.isControlledBy(source.getControllerId())
                || !(affectedAbility instanceof LoyaltyAbility)) {
            return false;
        }
        Permanent permanent = game.getPermanent(affectedAbility.getSourceId());
        return permanent != null && permanent.hasSubtype(SubType.JACE, game);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
