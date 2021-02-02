
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class HanweirLancer extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have first strike";

    public HanweirLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Hanweir Lancer is paired with another creature, both creatures have first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(FirstStrikeAbility.getInstance(), ruleText)));
    }

    private HanweirLancer(final HanweirLancer card) {
        super(card);
    }

    @Override
    public HanweirLancer copy() {
        return new HanweirLancer(this);
    }
}
