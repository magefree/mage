package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LeaveNoTrace extends CardImpl {
    static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public LeaveNoTrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Radiance - Destroy target enchantment and each other enchantment that shares a color with it.
        this.getSpellAbility().addEffect(new LeaveNoTraceEffect());
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    private LeaveNoTrace(final LeaveNoTrace card) {
        super(card);
    }

    @Override
    public LeaveNoTrace copy() {
        return new LeaveNoTrace(this);
    }
}

class LeaveNoTraceEffect extends OneShotEffect {
    static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    LeaveNoTraceEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target enchantment and each other enchantment that shares a color with it";
    }

    LeaveNoTraceEffect(final LeaveNoTraceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        if (target != null) {
            ObjectColor color = target.getColor(game);
            target.destroy(source, game, false);
            for (Permanent p : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (p.getColor(game).shares(color)) {
                    p.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LeaveNoTraceEffect copy() {
        return new LeaveNoTraceEffect(this);
    }
}
