package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HeartPiercerManticore extends CardImpl {

    public HeartPiercerManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Heart-Piercer Manticore enters the battlefield, you may sacrifice another creature. When you do, Heart-Piercer Manticore deals damage equal to that creature's power to any target.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HeartPiercerManticoreSacrificeEffect(), true));

        // Embalm {5}{R}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{5}{R}"), this));
    }

    private HeartPiercerManticore(final HeartPiercerManticore card) {
        super(card);
    }

    @Override
    public HeartPiercerManticore copy() {
        return new HeartPiercerManticore(this);
    }
}

class HeartPiercerManticoreSacrificeEffect extends OneShotEffect {

    HeartPiercerManticoreSacrificeEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice another creature. When you do, "
                + "{this} deals damage equal to that creature's power to any target";
    }

    private HeartPiercerManticoreSacrificeEffect(final HeartPiercerManticoreSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public HeartPiercerManticoreSacrificeEffect copy() {
        return new HeartPiercerManticoreSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent(
                1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        if (!controller.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent toSacrifice = game.getPermanent(target.getFirstTarget());
        if (toSacrifice == null) {
            return false;
        }
        int power = toSacrifice.getPower().getValue();
        if (!toSacrifice.sacrifice(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility trigger = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(power), false,
                "{this} deals damage equal to that creature's power to any target."
        );
        trigger.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(trigger, source);
        return true;
    }
}
