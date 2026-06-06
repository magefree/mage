package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class EpicFight extends CardImpl {

    public EpicFight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Double target creature's power and toughness until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new EpicFightEffect());

        // * Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addMode(new Mode(new FightTargetsEffect(false))
            .addTarget(new TargetControlledCreaturePermanent().withChooseHint("to fight"))
            .addTarget(new TargetOpponentsCreaturePermanent().withChooseHint("to fight")));
    }

    private EpicFight(final EpicFight card) {
        super(card);
    }

    @Override
    public EpicFight copy() {
        return new EpicFight(this);
    }
}

class EpicFightEffect extends OneShotEffect {

    EpicFightEffect() {
        super(Outcome.Benefit);
        staticText = "Double target creature's power and toughness until end of turn";
    }

    private EpicFightEffect(final EpicFightEffect effect) {
        super(effect);
    }

    @Override
    public EpicFightEffect copy() {
        return new EpicFightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        ContinuousEffect boost = new BoostTargetEffect(
            permanent.getPower().getValue(),
            permanent.getToughness().getValue()
        ).setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(boost, source);
        return true;
    }
}
