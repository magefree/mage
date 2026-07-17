package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YipYip extends CardImpl {

    public YipYip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        this.subtype.add(SubType.LESSON);

        // Target creature you control gets +2/+2 until end of turn. If that creature is an Ally, it also gains flying until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new YipYipEffect());
    }

    private YipYip(final YipYip card) {
        super(card);
    }

    @Override
    public YipYip copy() {
        return new YipYip(this);
    }
}

class YipYipEffect extends OneShotEffect {

    YipYipEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is an Ally, it also gains flying until end of turn";
    }

    private YipYipEffect(final YipYipEffect effect) {
        super(effect);
    }

    @Override
    public YipYipEffect copy() {
        return new YipYipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.hasSubtype(SubType.ALLY, game))
                .ifPresent(permanent -> game.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                        .setTargetPointer(new FixedTarget(permanent, game)), source));
        return true;
    }
}
