package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.BooToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinscBelovedRanger extends CardImpl {

    public MinscBelovedRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Minsc, Beloved Ranger enters the battlefield, create Boo, a legendary 1/1 red Hamster creature token with trample and haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BooToken())));

        // {X}: Until end of turn, target creature you control has base power and toughness X/X and becomes a Giant in addition to its other types. Activate only a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new MinscBelovedRangerEffect(), new ManaCostsImpl<>("{X}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private MinscBelovedRanger(final MinscBelovedRanger card) {
        super(card);
    }

    @Override
    public MinscBelovedRanger copy() {
        return new MinscBelovedRanger(this);
    }
}

class MinscBelovedRangerEffect extends OneShotEffect {

    MinscBelovedRangerEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, target creature you control has base power " +
                "and toughness X/X and becomes a Giant in addition to its other types";
    }

    private MinscBelovedRangerEffect(final MinscBelovedRangerEffect effect) {
        super(effect);
    }

    @Override
    public MinscBelovedRangerEffect copy() {
        return new MinscBelovedRangerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        game.addEffect(new SetBasePowerToughnessTargetEffect(xValue, xValue, Duration.EndOfTurn), source);
        game.addEffect(new AddCardSubTypeTargetEffect(SubType.GIANT, Duration.EndOfTurn), source);
        return true;
    }
}
