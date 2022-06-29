
package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.FearAbility;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(new ManaWasSpentCondition(ColoredManaSymbol.B)), false));

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

    SquealingDevilEffect(final SquealingDevilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        if (player != null) {
            if (player.chooseUse(Outcome.BoostCreature, "Pay " + cost.getText() + "?", source, game)) {
                int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    Permanent permanent = game.getPermanent(source.getFirstTarget());
                    if (permanent != null && permanent.isCreature(game)) {
                        ContinuousEffect effect = new BoostTargetEffect(costX, 0, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public SquealingDevilEffect copy() {
        return new SquealingDevilEffect(this);
    }

}
