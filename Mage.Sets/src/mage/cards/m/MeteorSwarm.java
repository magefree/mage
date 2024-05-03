package mage.cards.m;

import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author Raphael-Schulz
 */
public final class MeteorSwarm extends CardImpl {

    public MeteorSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");

        // Meteor Swarm deals 8 damage divided as you choose among X target creatures and/or planeswalkers.
        this.getSpellAbility().addEffect(
                new DamageMultiEffect(8).
                        setText("{this} deals 8 damage divided as you choose among X target creatures and/or planeswalkers.")
        );
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalkerAmount(8));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private MeteorSwarm(final MeteorSwarm card) {
        super(card);
    }

    @Override
    public MeteorSwarm copy() {
        return new MeteorSwarm(this);
    }
}
