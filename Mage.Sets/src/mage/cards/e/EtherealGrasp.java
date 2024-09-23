
package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityTargetPerpetuallyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public final class EtherealGrasp extends CardImpl {

    public EtherealGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Tap target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());

        // That creature perpetually gains “This creature doesn’t untap during your untap step” and “{8}: Untap this creature.”
        this.getSpellAbility().addEffect(new EtherealGraspEffect());
    }

    private EtherealGrasp(final EtherealGrasp card) {
        super(card);
    }

    @Override
    public EtherealGrasp copy() {
        return new EtherealGrasp(this);
    }
}

class EtherealGraspEffect extends OneShotEffect {

    EtherealGraspEffect() {
        super(Outcome.AddAbility);
        this.staticText = "That creature perpetually gains “This creature doesn’t untap during your untap step” and “{8}: Untap this creature.”";
    }

    private EtherealGraspEffect(final EtherealGraspEffect effect) {
        super(effect);
    }

    @Override
    public EtherealGraspEffect copy() {
        return new EtherealGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        game.addEffect(new GainAbilityTargetPerpetuallyEffect(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()),
                "creature perpetually gains “This creature doesn’t untap during your untap step”"
        ).setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);

        game.addEffect(new GainAbilityTargetPerpetuallyEffect(
                new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new GenericManaCost(8))
        ).setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);

        return true;
    }
}

