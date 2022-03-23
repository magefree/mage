package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HarmonyOfNature extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public HarmonyOfNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way.
        this.getSpellAbility().addEffect(new HarmonyOfNatureEffect());
        this.getSpellAbility().addHint(new ValueHint(filter.getMessage(), new PermanentsOnBattlefieldCount(filter)));
    }

    private HarmonyOfNature(final HarmonyOfNature card) {
        super(card);
    }

    @Override
    public HarmonyOfNature copy() {
        return new HarmonyOfNature(this);
    }
}

class HarmonyOfNatureEffect extends OneShotEffect {

    public HarmonyOfNatureEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Tap any number of untapped creatures you control. You gain 4 life for each creature tapped this way";
    }

    public HarmonyOfNatureEffect(HarmonyOfNatureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetPermanent target = new TargetControlledPermanent(0, Integer.MAX_VALUE, HarmonyOfNature.filter, true);
        controller.choose(outcome, target, source, game);
        if (target.getTargets().isEmpty()) {
            return false;
        }

        int tappedAmount = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.tap(source, game)) {
                tappedAmount++;
            }
        }

        if (tappedAmount > 0) {
            controller.gainLife(tappedAmount * 4, game, source);
        }
        return true;
    }

    @Override
    public HarmonyOfNatureEffect copy() {
        return new HarmonyOfNatureEffect(this);
    }

}
