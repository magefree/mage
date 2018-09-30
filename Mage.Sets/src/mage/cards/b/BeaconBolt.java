package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.InstantSorceryExileGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class BeaconBolt extends CardImpl {

    public BeaconBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Beacon Bolt deals damage to target creature equal to the total number of instant and sorcery cards you own in exile and in your graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                InstantSorceryExileGraveyardCount.instance
        ).setText("{this} deals damage to target creature equal to "
                + "the total number of instant and sorcery cards "
                + "you own in exile and in your graveyard"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    public BeaconBolt(final BeaconBolt card) {
        super(card);
    }

    @Override
    public BeaconBolt copy() {
        return new BeaconBolt(this);
    }
}
