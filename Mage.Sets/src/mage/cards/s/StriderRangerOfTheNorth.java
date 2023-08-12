package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TiagoMDG
 */
public final class StriderRangerOfTheNorth extends CardImpl {

    public StriderRangerOfTheNorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Landfall -- Whenever a land enters the battlefield under your control,
        // target creature gets +1/+1 until end of turn.
        // Then if that creature has power 4 or greater, it gains first strike until end of turn.

        LandfallAbility ability = new LandfallAbility(
                new BoostTargetEffect(1, 1, Duration.EndOfTurn), false
        );

        ability.addEffect(new StriderRangerOfTheNorthEffect());

        ability.addTarget(new TargetCreaturePermanent());

        this.addAbility(ability);
    }

    private StriderRangerOfTheNorth(final StriderRangerOfTheNorth card) {
        super(card);
    }

    @Override
    public StriderRangerOfTheNorth copy() {
        return new StriderRangerOfTheNorth(this);
    }
}

class StriderRangerOfTheNorthEffect extends OneShotEffect {

    StriderRangerOfTheNorthEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if that creature has power 4 " +
                "or greater, it gains first strike until end of turn.";
    }

    StriderRangerOfTheNorthEffect(final StriderRangerOfTheNorthEffect effect) {
        super(effect);
    }

    @Override
    public StriderRangerOfTheNorthEffect copy() {
        return new StriderRangerOfTheNorthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        // Checks if the creature's power is 4 or greater and then add First Strike until end of turn
        if (permanent.getPower().getValue() >= 4) {
            ContinuousEffect firstStrike = new GainAbilityTargetEffect(
                    FirstStrikeAbility.getInstance(), Duration.EndOfTurn);

            firstStrike.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(firstStrike, source);
        }

        return true;
    }
}
