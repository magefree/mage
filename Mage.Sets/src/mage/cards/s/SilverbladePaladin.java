
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class SilverbladePaladin extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have double strike";

    public SilverbladePaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Silverblade Paladin is paired with another creature, both creatures have double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(DoubleStrikeAbility.getInstance(), ruleText)));
    }

    private SilverbladePaladin(final SilverbladePaladin card) {
        super(card);
    }

    @Override
    public SilverbladePaladin copy() {
        return new SilverbladePaladin(this);
    }
}
