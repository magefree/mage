package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VincentsLimitBreak extends CardImpl {

    public VincentsLimitBreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // Until end of turn, target creature you control gains "When this creature dies, return it to the battlefield tapped under its owner's control" and has the chosen base power and toughness.
        this.getSpellAbility().getModes().setChooseText("Tiered <i>(Choose one additional cost.)</i><br>" +
                "Until end of turn, target creature you control gains \"When this creature dies, return it " +
                "to the battlefield tapped under its owner's control\" and has the chosen base power and toughness.");

        // * Galian Beast -- {0} -- 3/2.
        this.getSpellAbility().addEffect(new VincentsLimitBreakEffect(3, 2));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Galian Beast");
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));

        // * Death Gigas -- {1} -- 5/2.
        this.getSpellAbility().addMode(new Mode(new VincentsLimitBreakEffect(5, 2))
                .addTarget(new TargetControlledCreaturePermanent())
                .withFlavorWord("Death Gigas")
                .withCost(new GenericManaCost(1)));

        // * Hellmasker -- {3} -- 7/2
        this.getSpellAbility().addMode(new Mode(new VincentsLimitBreakEffect(7, 2))
                .addTarget(new TargetControlledCreaturePermanent())
                .withFlavorWord("Hellmasker")
                .withCost(new GenericManaCost(3)));
    }

    private VincentsLimitBreak(final VincentsLimitBreak card) {
        super(card);
    }

    @Override
    public VincentsLimitBreak copy() {
        return new VincentsLimitBreak(this);
    }
}

class VincentsLimitBreakEffect extends OneShotEffect {

    private final int power;
    private final int toughness;

    VincentsLimitBreakEffect(int power, int toughness) {
        super(Outcome.Benefit);
        staticText = power + "/" + toughness + '.';
        this.power = power;
        this.toughness = toughness;
    }

    private VincentsLimitBreakEffect(final VincentsLimitBreakEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public VincentsLimitBreakEffect copy() {
        return new VincentsLimitBreakEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(new DiesSourceTriggeredAbility(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, true), false
        )).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new SetBasePowerToughnessTargetEffect(
                power, toughness, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
