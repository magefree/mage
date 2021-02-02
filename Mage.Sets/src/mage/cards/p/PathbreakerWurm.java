
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class PathbreakerWurm extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have trample";

    public PathbreakerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Pathbreaker Wurm is paired with another creature, both creatures have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(TrampleAbility.getInstance(), ruleText)));

    }

    private PathbreakerWurm(final PathbreakerWurm card) {
        super(card);
    }

    @Override
    public PathbreakerWurm copy() {
        return new PathbreakerWurm(this);
    }
}
