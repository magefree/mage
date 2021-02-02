
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class SpectralGateguards extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have vigilance";

    public SpectralGateguards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Spectral Gateguards is paired with another creature, both creatures have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(VigilanceAbility.getInstance(), ruleText)));
    }

    private SpectralGateguards(final SpectralGateguards card) {
        super(card);
    }

    @Override
    public SpectralGateguards copy() {
        return new SpectralGateguards(this);
    }
}
