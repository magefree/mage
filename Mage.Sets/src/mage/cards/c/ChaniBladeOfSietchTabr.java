package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class ChaniBladeOfSietchTabr extends CardImpl {

    public ChaniBladeOfSietchTabr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
            new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Landfall -- Whenever a land you control enters, put a +1/+1 counter on target creature. If that land is a Desert, that creature gains vigilance, hexproof, and haste until end of turn.
        Ability ability = new LandfallAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addEffect(new ChaniBladeOfSietchTabrDesertEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ChaniBladeOfSietchTabr(final ChaniBladeOfSietchTabr card) {
        super(card);
    }

    @Override
    public ChaniBladeOfSietchTabr copy() {
        return new ChaniBladeOfSietchTabr(this);
    }
}

class ChaniBladeOfSietchTabrDesertEffect extends OneShotEffect {

    ChaniBladeOfSietchTabrDesertEffect() {
        super(Outcome.AddAbility);
        staticText = "If that land is a Desert, that creature gains vigilance, hexproof, and haste until end of turn";
    }

    private ChaniBladeOfSietchTabrDesertEffect(final ChaniBladeOfSietchTabrDesertEffect effect) {
        super(effect);
    }

    @Override
    public ChaniBladeOfSietchTabrDesertEffect copy() {
        return new ChaniBladeOfSietchTabrDesertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeringLand = ((LandfallAbility) source).getTriggeringPermanent();
        if (triggeringLand == null) {
            return false;
        }
        if (!triggeringLand.hasSubtype(SubType.DESERT, game)) {
            return true;
        }

        game.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance()), source);
        game.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance()), source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()), source);
        return true;
    }
}
