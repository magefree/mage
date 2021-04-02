package mage.cards.b;

import mage.abilities.dynamicvalue.common.InstantSorceryExileGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeaconBolt extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instant and sorcery cards in your exile and graveyard", InstantSorceryExileGraveyardCount.instance
    );

    public BeaconBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Beacon Bolt deals damage to target creature equal to the total number of instant and sorcery cards you own in exile and in your graveyard.
        this.getSpellAbility().addEffect(new DamageTargetEffect(
                InstantSorceryExileGraveyardCount.instance
        ).setText("{this} deals damage to target creature equal to "
                + "the total number of instant and sorcery cards "
                + "you own in exile and in your graveyard"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);

        // Jump-start
        this.addAbility(new JumpStartAbility(this));
    }

    private BeaconBolt(final BeaconBolt card) {
        super(card);
    }

    @Override
    public BeaconBolt copy() {
        return new BeaconBolt(this);
    }
}
