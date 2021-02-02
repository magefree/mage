
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class NightshadePeddler extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have deathtouch";

    public NightshadePeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Nightshade Peddler is paired with another creature, both creatures have deathtouch.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(DeathtouchAbility.getInstance(), ruleText)));
    }

    private NightshadePeddler(final NightshadePeddler card) {
        super(card);
    }

    @Override
    public NightshadePeddler copy() {
        return new NightshadePeddler(this);
    }
}
