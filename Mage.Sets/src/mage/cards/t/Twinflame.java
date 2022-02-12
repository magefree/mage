package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Twinflame extends CardImpl {

    public Twinflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Strive - Twinflame costs 2R more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{R}"));

        // Choose any number of target creatures you control. For each of them, create a token that's a copy of that creature, except it has haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new TwinflameCopyEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, new FilterControlledCreaturePermanent(), false));

    }

    private Twinflame(final Twinflame card) {
        super(card);
    }

    @Override
    public Twinflame copy() {
        return new Twinflame(this);
    }
}

class TwinflameCopyEffect extends OneShotEffect {

    public TwinflameCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "choose any number of target creatures you control. For each of them, create a token that's a copy of that creature, except it has haste. Exile those tokens at the beginning of the next end step";
    }

    public TwinflameCopyEffect(final TwinflameCopyEffect effect) {
        super(effect);
    }

    @Override
    public TwinflameCopyEffect copy() {
        return new TwinflameCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toExile = new ArrayList<>();
            for (UUID creatureId : this.getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
                if (creature != null) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    effect.apply(game, source);
                    toExile.addAll(effect.getAddedPermanents());
                }
            }
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(new FixedTargets(toExile, game));
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return false;
    }
}
