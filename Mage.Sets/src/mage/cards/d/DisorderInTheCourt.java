package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisorderInTheCourt extends CardImpl {

    public DisorderInTheCourt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{U}");

        // Exile X target creatures, then investigate X times. Return the exiled cards to the battlefield tapped under their owners' control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect()
                .setText("exile X target creatures"));
        this.getSpellAbility().addEffect(new InvestigateEffect(ManacostVariableValue.REGULAR)
                .setText(", then investigate X times"));
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, true)
                .setText("Return the exiled cards to the battlefield tapped under their owners' control at the beginning of the next end step"));
        this.getSpellAbility().setTargetAdjuster(DisorderInTheCourtAdjuster.instance);
    }

    private DisorderInTheCourt(final DisorderInTheCourt card) {
        super(card);
    }

    @Override
    public DisorderInTheCourt copy() {
        return new DisorderInTheCourt(this);
    }
}

enum DisorderInTheCourtAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}
