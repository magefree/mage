package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MightformHarmonizer extends CardImpl {

    public MightformHarmonizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land you control enters, double the power of target creature you control until end of turn.
        Ability ability = new LandfallAbility(new MightformHarmonizerEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Warp {2}{G}
        this.addAbility(new WarpAbility(this, "{2}{G}"));
    }

    private MightformHarmonizer(final MightformHarmonizer card) {
        super(card);
    }

    @Override
    public MightformHarmonizer copy() {
        return new MightformHarmonizer(this);
    }
}

class MightformHarmonizerEffect extends OneShotEffect {

    MightformHarmonizerEffect() {
        super(Outcome.Benefit);
        staticText = "double the power of target creature you control until end of turn";
    }

    private MightformHarmonizerEffect(final MightformHarmonizerEffect effect) {
        super(effect);
    }

    @Override
    public MightformHarmonizerEffect copy() {
        return new MightformHarmonizerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getPower().getValue() == 0) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(permanent.getPower().getValue(), 0)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
