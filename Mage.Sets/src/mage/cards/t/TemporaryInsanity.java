
package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author FenrisulfrX
 */
public final class TemporaryInsanity extends CardImpl {

    public TemporaryInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        // Untap target creature with power less than the number of cards in your graveyard
        this.getSpellAbility().addTarget(new TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard());
        this.getSpellAbility().addEffect(new UntapTargetEffect());

        // and gain control of it until end of turn.
        Effect effect = new GainControlTargetEffect(Duration.EndOfTurn);
        effect.setText("and gain control of it until end of turn. ");
        this.getSpellAbility().addEffect(effect);

        // That creature gains haste until end of turn.
        effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("That creature gains haste until end of turn.");
        this.getSpellAbility().addEffect(effect);
    }

    private TemporaryInsanity(final TemporaryInsanity card) {
        super(card);
    }

    @Override
    public TemporaryInsanity copy() {
        return new TemporaryInsanity(this);
    }
}

class TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard extends TargetCreaturePermanent {

    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard() {
        super();
        targetName = "creature with power less than the number of cards in your graveyard";
    }

    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard(final TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent target = game.getPermanent(id);
            if (target != null) {
                return target.getPower().getValue() < game.getPlayer(source.getControllerId()).getGraveyard().size();
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        MageObject targetSource = game.getObject(source);
        if(targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                    if (permanent.getPower().getValue() < game.getPlayer(sourceControllerId).getGraveyard().size()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard copy() {
        return new TargetCreatureWithPowerLessThanNumberOfCardsInYourGraveyard(this);
    }
}
