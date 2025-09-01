package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquealingDevil extends CardImpl {

    public SquealingDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // When Squealing Devil enters the battlefield, you may pay {X}. If you do, target creature gets +X/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SquealingDevilEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Squealing Devil enters the battlefield, sacrifice it unless {B} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(ManaWasSpentCondition.BLACK), false));

    }

    private SquealingDevil(final SquealingDevil card) {
        super(card);
    }

    @Override
    public SquealingDevil copy() {
        return new SquealingDevil(this);
    }
}

class SquealingDevilEffect extends OneShotEffect {

    SquealingDevilEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. If you do, target creature gets +X/+0 until end of turn.";
    }

    private SquealingDevilEffect(final SquealingDevilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(Outcome.BoostCreature, "Pay {X}?", source, game)) {
            return false;
        }
        int xValue = player.announceX(0, Integer.MAX_VALUE, "Announce the value for {X} (pay to boost)", game, source, true);
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        cost.add(new GenericManaCost(xValue));
        if (!cost.pay(source, game, source, source.getControllerId(), false, null)) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(xValue, 0, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }

    @Override
    public SquealingDevilEffect copy() {
        return new SquealingDevilEffect(this);
    }

}
