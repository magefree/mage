package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandstormCrasher extends CardImpl {

    public SandstormCrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may exert Sandstorm Crasher as it attacks. When you do, create a tapped and attacking token that's a copy of target creature you control. Sacrifice the token at the beginning of the next end step.
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(new SandstormCrasherEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new ExertAbility(ability));
    }

    private SandstormCrasher(final SandstormCrasher card) {
        super(card);
    }

    @Override
    public SandstormCrasher copy() {
        return new SandstormCrasher(this);
    }
}

class SandstormCrasherEffect extends OneShotEffect {

    SandstormCrasherEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of target creature you control. " +
                "Sacrifice the token at the beginning of the next end step";
    }

    private SandstormCrasherEffect(final SandstormCrasherEffect effect) {
        super(effect);
    }

    @Override
    public SandstormCrasherEffect copy() {
        return new SandstormCrasherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, true
        );
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
