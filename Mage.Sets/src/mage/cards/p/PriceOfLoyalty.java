package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PriceOfLoyalty extends CardImpl {

    public PriceOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. If mana from a Treasure was spent to cast this spell, that creature gets +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new PriceOfLoyaltyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PriceOfLoyalty(final PriceOfLoyalty card) {
        super(card);
    }

    @Override
    public PriceOfLoyalty copy() {
        return new PriceOfLoyalty(this);
    }
}

class PriceOfLoyaltyEffect extends OneShotEffect {

    PriceOfLoyaltyEffect() {
        super(Outcome.Benefit);
        staticText = "If mana from a Treasure was spent to cast this spell, " +
                "that creature gets +2/+0 until end of turn";
    }

    private PriceOfLoyaltyEffect(final PriceOfLoyaltyEffect effect) {
        super(effect);
    }

    @Override
    public PriceOfLoyaltyEffect copy() {
        return new PriceOfLoyaltyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ManaPaidSourceWatcher.getTreasurePaid(source.getId(), game) > 0) {
            game.addEffect(new BoostTargetEffect(2, 0), source);
            return true;
        }
        return false;
    }
}
