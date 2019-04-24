package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassManipulation extends CardImpl {

    public MassManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}{U}{U}");

        // Gain control of X target creatures and/or planeswalkers.
        this.getSpellAbility().addEffect(
                new GainControlTargetEffect(Duration.Custom, true)
                        .setText("Gain control of X target creatures and/or planeswalkers.")
        );
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().setTargetAdjuster(MassManipulationAdjuster.instance);
    }

    private MassManipulation(final MassManipulation card) {
        super(card);
    }

    @Override
    public MassManipulation copy() {
        return new MassManipulation(this);
    }
}

enum MassManipulationAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreatureOrPlaneswalker(ability.getManaCostsToPay().getX()));
    }
}
