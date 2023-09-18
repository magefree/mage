package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class InfernoOfTheStarMounts extends CardImpl {

    public InfernoOfTheStarMounts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {R}: Inferno of the Star Mounts gets +1/+0 until end of turn. When its power becomes 20 this way, it deals 20 damage to any target.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new InfernoOfTheStarMountsEffect());
        this.addAbility(ability);
    }

    private InfernoOfTheStarMounts(final InfernoOfTheStarMounts card) {
        super(card);
    }

    @Override
    public InfernoOfTheStarMounts copy() {
        return new InfernoOfTheStarMounts(this);
    }
}

class InfernoOfTheStarMountsEffect extends OneShotEffect {

    public InfernoOfTheStarMountsEffect() {
        super(Outcome.Damage);
        this.staticText = "When its power becomes 20 this way, it deals 20 damage to any target";
    }

    private InfernoOfTheStarMountsEffect(final InfernoOfTheStarMountsEffect effect) {
        super(effect);
    }

    @Override
    public InfernoOfTheStarMountsEffect copy() {
        return new InfernoOfTheStarMountsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.getPower().getValue() == 20) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new DamageTargetEffect(20), false, this.staticText
            );
            ability.addTarget(new TargetAnyTarget());
            game.fireReflexiveTriggeredAbility(ability, source);
            return true;
        }
        return false;
    }
}
