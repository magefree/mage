package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.predicate.permanent.EnchantmentOrEnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarametrasBlessing extends CardImpl {

    public KarametrasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 until end of turn. If it's an enchanted creature or enchantment creature, it also gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addEffect(new KarametrasBlessingEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KarametrasBlessing(final KarametrasBlessing card) {
        super(card);
    }

    @Override
    public KarametrasBlessing copy() {
        return new KarametrasBlessing(this);
    }
}

class KarametrasBlessingEffect extends OneShotEffect {

    KarametrasBlessingEffect() {
        super(Outcome.Benefit);
        staticText = "If it's an enchanted creature or enchantment creature, " +
                "it also gains hexproof and indestructible until end of turn.";
    }

    private KarametrasBlessingEffect(final KarametrasBlessingEffect effect) {
        super(effect);
    }

    @Override
    public KarametrasBlessingEffect copy() {
        return new KarametrasBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null || !EnchantmentOrEnchantedPredicate.instance.apply(permanent, game)) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn), source);
        game.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}
