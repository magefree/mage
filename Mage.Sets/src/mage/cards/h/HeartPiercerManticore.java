package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
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
import mage.players.Player;

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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HeartPiercerManticoreEffect()));

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

class HeartPiercerManticoreEffect extends OneShotEffect {

    HeartPiercerManticoreEffect() {
        super(Outcome.Damage);
        this.staticText = "you may sacrifice another creature. When you do, "
                + "{this} deals damage equal to that creature's power to any target";
    }

    private HeartPiercerManticoreEffect(final HeartPiercerManticoreEffect effect) {
        super(effect);
    }

    @Override
    public HeartPiercerManticoreEffect copy() {
        return new HeartPiercerManticoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        SacrificeTargetCost cost = new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE);
        if (!cost.canPay(source, source, source.getControllerId(), game)
                || !controller.chooseUse(outcome, "Sacrifice another creature?", source, game)
                || !cost.pay(source, game, source, source.getControllerId(), false)) {
            return false;
        }
        int power = cost
                .getPermanents()
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        ReflexiveTriggeredAbility trigger = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(power), false,
                "{this} deals damage equal to that creature's power to any target."
        );
        return true;
    }
}
