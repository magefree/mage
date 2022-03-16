
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class CogworkAssembler extends CardImpl {

    public CogworkAssembler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        
        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {7}: Create a token that's a copy of target artifact. That token gains haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CogworkAssemblerCreateTokenEffect(), new GenericManaCost(7));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private CogworkAssembler(final CogworkAssembler card) {
        super(card);
    }

    @Override
    public CogworkAssembler copy() {
        return new CogworkAssembler(this);
    }
}

class CogworkAssemblerCreateTokenEffect extends OneShotEffect {

    CogworkAssemblerCreateTokenEffect() {
        super(Outcome.Copy);
        this.staticText = "Create a token that's a copy of target artifact. That token gains haste. Exile it at the beginning of the next end step";
    }

    CogworkAssemblerCreateTokenEffect(final CogworkAssemblerCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public CogworkAssemblerCreateTokenEffect copy() {
        return new CogworkAssemblerCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copiedPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (copiedPermanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, CardType.ARTIFACT, true);
            if (effect.apply(game, source)) {
                for (Permanent copyPermanent : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(copyPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }
        return false;
    }
}
