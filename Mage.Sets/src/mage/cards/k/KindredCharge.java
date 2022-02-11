
package mage.cards.k;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public final class KindredCharge extends CardImpl {

    public KindredCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Choose a creature type. For each creature you control of the chosen type, create a token that's a copy of that creature.
        // Those tokens gain haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.Copy));
        this.getSpellAbility().addEffect(new KindredChargeEffect());
    }

    private KindredCharge(final KindredCharge card) {
        super(card);
    }

    @Override
    public KindredCharge copy() {
        return new KindredCharge(this);
    }
}

class KindredChargeEffect extends OneShotEffect {

    public KindredChargeEffect() {
        super(Outcome.Copy);
        this.staticText = "For each creature you control of the chosen type, create a token that's a copy of that creature. "
                + "Those tokens gain haste. Exile them at the beginning of the next end step";
    }

    public KindredChargeEffect(final KindredChargeEffect effect) {
        super(effect);
    }

    @Override
    public KindredChargeEffect copy() {
        return new KindredChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subType != null) {
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control of the chosen type");
                filter.add(subType.getPredicate());
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                    if (permanent != null) {
                        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        effect.apply(game, source);
                        for (Permanent addedToken : effect.getAddedPermanents()) {
                            Effect exileEffect = new ExileTargetEffect();
                            exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
