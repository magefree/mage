package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsNoSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class BlazeOfGlory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsNoSourcePredicate.instance);
    }

    public BlazeOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Cast Blaze of Glory only during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, BeforeBlockersAreDeclaredCondition.instance));

        // Target creature defending player controls can block any number of creatures this turn. It blocks each attacking creature this turn if able.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new CanBlockAdditionalCreatureTargetEffect(Duration.EndOfTurn, 0));
        this.getSpellAbility().addEffect(new BlazeOfGloryRequirementEffect());
    }

    private BlazeOfGlory(final BlazeOfGlory card) {
        super(card);
    }

    @Override
    public BlazeOfGlory copy() {
        return new BlazeOfGlory(this);
    }
}

class BlazeOfGloryRequirementEffect extends RequirementEffect {

    public BlazeOfGloryRequirementEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "It blocks each attacking creature this turn if able";
    }

    private BlazeOfGloryRequirementEffect(final BlazeOfGloryRequirementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public boolean mustBlockAllAttackers(Game game) {
        return true;
    }

    @Override
    public BlazeOfGloryRequirementEffect copy() {
        return new BlazeOfGloryRequirementEffect(this);
    }

}
