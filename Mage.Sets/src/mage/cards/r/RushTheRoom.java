package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RushTheRoom extends CardImpl {

    public RushTheRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +1/+0 and gains first strike until end of turn. If it's a Goblin or Orc, it also gains haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike until end of turn"));
        this.getSpellAbility().addEffect(new RushTheRoomEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RushTheRoom(final RushTheRoom card) {
        super(card);
    }

    @Override
    public RushTheRoom copy() {
        return new RushTheRoom(this);
    }
}

class RushTheRoomEffect extends OneShotEffect {

    RushTheRoomEffect() {
        super(Outcome.Benefit);
        staticText = "If it's a Goblin or Orc, it also gains haste until end of turn";
    }

    private RushTheRoomEffect(final RushTheRoomEffect effect) {
        super(effect);
    }

    @Override
    public RushTheRoomEffect copy() {
        return new RushTheRoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && (permanent.hasSubtype(SubType.GOBLIN, game) || permanent.hasSubtype(SubType.ORC, game))) {
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
            return true;
        }
        return false;
    }
}
