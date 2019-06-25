package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorBarrier extends CardImpl {

    public RazorBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target permanent you control gains protection from artifacts or from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new RazorBarrierEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private RazorBarrier(final RazorBarrier card) {
        super(card);
    }

    @Override
    public RazorBarrier copy() {
        return new RazorBarrier(this);
    }
}

class RazorBarrierEffect extends OneShotEffect {

    private static final FilterObject filter = new FilterObject("colorless");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    RazorBarrierEffect() {
        super(Outcome.Benefit);
        staticText = "Target permanent you control gains protection from artifacts " +
                "or from the color of your choice until end of turn.";
    }

    private RazorBarrierEffect(final RazorBarrierEffect effect) {
        super(effect);
    }

    @Override
    public RazorBarrierEffect copy() {
        return new RazorBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Give the targeted permanent protection from artifacts?", null, "Yes", "No (choose a color instead)", source, game)) {
            game.addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn), source);
            return true;
        }
        game.addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), source);
        return true;
    }
}
