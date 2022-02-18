package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class MoltenEchoes extends CardImpl {

    public MoltenEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // As Molten Echoes enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Copy)));

        // Whenever a nontoken creature of the chosen type enters the battlefield under your control, create a token that's a copy of that creature. That token gains haste. Exile it at the beginning of the next end step.
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("nontoken creature of the chosen type");
        filter.add(TokenPredicate.FALSE);
        filter.add(ChosenSubtypePredicate.TRUE);

        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new MoltenEchoesEffect(),
                filter, false, SetTargetPointer.PERMANENT,
                "Whenever a nontoken creature of the chosen type enters the battlefield under your control, "
                        + "create a token that's a copy of that creature. "
                        + "That token gains haste. Exile it at the beginning of the next end step.");
        this.addAbility(ability);
    }

    private MoltenEchoes(final MoltenEchoes card) {
        super(card);
    }

    @Override
    public MoltenEchoes copy() {
        return new MoltenEchoes(this);
    }
}

class MoltenEchoesEffect extends OneShotEffect {

    public MoltenEchoesEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that creature. That token gains haste. Exile it at the beginning of the next end step";
    }

    public MoltenEchoesEffect(final MoltenEchoesEffect effect) {
        super(effect);
    }

    @Override
    public MoltenEchoesEffect copy() {
        return new MoltenEchoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
            effect.setTargetPointer(getTargetPointer());
            if (effect.apply(game, source)) {
                for (Permanent tokenPermanent : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }

        return false;
    }
}

