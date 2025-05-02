package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class UnrulyKrasis extends CardImpl {

    public UnrulyKrasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Unruly Krasis attacks, you may have the base power and toughness of another target creature you control become X/X until end of turn, where X is Unruly Krasis's power.
        Ability ability = new AttacksTriggeredAbility(new UnrulyKrasisEffect(), true);
        ability.addTarget(new TargetControlledCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // {3}{G}{U}: Adapt 3.
        this.addAbility(new AdaptAbility(3, "{3}{G}{U}"));
    }

    private UnrulyKrasis(final UnrulyKrasis card) {
        super(card);
    }

    @Override
    public UnrulyKrasis copy() {
        return new UnrulyKrasis(this);
    }
}

class UnrulyKrasisEffect extends OneShotEffect {

    UnrulyKrasisEffect() {
        super(Outcome.BoostCreature);
        staticText = "have the base power and toughness of another target creature you control "
                + "become X/X until end of turn, where X is {this}'s power";
    }

    private UnrulyKrasisEffect(final UnrulyKrasisEffect effect) {
        super(effect);
    }

    @Override
    public UnrulyKrasisEffect copy() {
        return new UnrulyKrasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }

        int xValue = permanent.getPower().getValue();
        ContinuousEffect effect = new SetBasePowerToughnessTargetEffect(xValue, xValue, Duration.EndOfTurn);
        effect.setTargetPointer(getTargetPointer().copy());
        game.addEffect(effect, source);
        return true;
    }
}