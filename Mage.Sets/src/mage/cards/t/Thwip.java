package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Thwip extends CardImpl {

    public Thwip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +2/+2 and gains flying until end of turn. If it's a Spider, you gain 2 life.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains flying until end of turn"));
        this.getSpellAbility().addEffect(new ThwipEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Thwip(final Thwip card) {
        super(card);
    }

    @Override
    public Thwip copy() {
        return new Thwip(this);
    }
}

class ThwipEffect extends OneShotEffect {

    ThwipEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Spider, you gain 2 life";
    }

    private ThwipEffect(final ThwipEffect effect) {
        super(effect);
    }

    @Override
    public ThwipEffect copy() {
        return new ThwipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null
                && permanent.hasSubtype(SubType.SPIDER, game)
                && Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.gainLife(2, game, source) > 0)
                .isPresent();
    }
}
