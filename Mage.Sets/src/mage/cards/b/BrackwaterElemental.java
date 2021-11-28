
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class BrackwaterElemental extends CardImpl {

    public BrackwaterElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Brackwater Elemental attacks or blocks, sacrifice it at the beginning of the next end step.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BrackwaterElementalSacrificeEffect(), false));
        // Unearth {2}{U}
        this.addAbility(new UnearthAbility(new ManaCostsImpl("{2}{U}")));
    }

    private BrackwaterElemental(final BrackwaterElemental card) {
        super(card);
    }

    @Override
    public BrackwaterElemental copy() {
        return new BrackwaterElemental(this);
    }
}

class BrackwaterElementalSacrificeEffect extends OneShotEffect {

    public BrackwaterElementalSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice it at the beginning of the next end step";
    }

    public BrackwaterElementalSacrificeEffect(final BrackwaterElementalSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public BrackwaterElementalSacrificeEffect copy() {
        return new BrackwaterElementalSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice {this}");
            sacrificeEffect.setTargetPointer(new FixedTarget(sourcePermanent.getId(), game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        }
        return false;
    }
}
