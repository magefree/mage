package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.OpponentsCantCastChosenUntilNextTurnEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author htrajan
 */
public final class AcademicProbation extends CardImpl {

    public AcademicProbation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");
        
        this.subtype.add(SubType.LESSON);

        // Choose one —
        // • Choose a nonland card name. Opponents can't cast spells with the chosen name until your next turn.
        Effect effect = new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME);
        effect.setText("Choose a nonland card name");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new OpponentsCantCastChosenUntilNextTurnEffect().setText("opponents can't cast spells with the chosen name until your next turn"));

        // • Choose target nonland permanent. Until your next turn, it can't attack or block, and its activated abilities can't be activated.
        Mode restrictMode = new Mode(new AcademicProbationRestrictionEffect());
        restrictMode.addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addMode(restrictMode);
    }

    private AcademicProbation(final AcademicProbation card) {
        super(card);
    }

    @Override
    public AcademicProbation copy() {
        return new AcademicProbation(this);
    }
}

class AcademicProbationRestrictionEffect extends RestrictionEffect {

    AcademicProbationRestrictionEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
        staticText = "choose target nonland permanent. Until your next turn, it can't attack or block, and its activated abilities can't be activated";
    }

    AcademicProbationRestrictionEffect(final AcademicProbationRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public AcademicProbationRestrictionEffect copy() {
        return new AcademicProbationRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }
}