package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.LifeTotalCantChangeControllerEffect;
import mage.abilities.keyword.GiftAbility;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SwanSongBirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerchProtection extends CardImpl {

    public PerchProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}{W}");

        // Gift an extra turn
        this.addAbility(new GiftAbility(this, GiftType.EXTRA_TURN));

        // Create four 2/2 blue Bird creature tokens with flying. If the gift was promised, all permanents you control phase out, and until your next turn, your life total can't change and you gain protection from everything.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SwanSongBirdToken(), 4));
        this.getSpellAbility().addEffect(new PerchProtectionEffect());

        // Exile Perch Protection.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private PerchProtection(final PerchProtection card) {
        super(card);
    }

    @Override
    public PerchProtection copy() {
        return new PerchProtection(this);
    }
}

class PerchProtectionEffect extends OneShotEffect {

    PerchProtectionEffect() {
        super(Outcome.Benefit);
        staticText = "if the gift was promised, all permanents you control phase out, " +
                "and until your next turn, your life total can't change and you gain protection from everything";
    }

    private PerchProtectionEffect(final PerchProtectionEffect effect) {
        super(effect);
    }

    @Override
    public PerchProtectionEffect copy() {
        return new PerchProtectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (GiftWasPromisedCondition.FALSE.apply(game, source)) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT, source.getControllerId(), source, game
        )) {
            permanent.phaseOut(game);
        }
        game.addEffect(new LifeTotalCantChangeControllerEffect(Duration.UntilYourNextTurn), source);
        game.addEffect(new GainAbilityControllerEffect(
                new ProtectionFromEverythingAbility(), Duration.UntilYourNextTurn
        ), source);
        return true;
    }
}
