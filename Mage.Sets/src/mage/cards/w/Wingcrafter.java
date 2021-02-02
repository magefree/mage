
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author noxx

 */
public final class Wingcrafter extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have flying";

    public Wingcrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Wingcrafter is paired with another creature, both creatures have flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(FlyingAbility.getInstance(), ruleText)));
    }

    private Wingcrafter(final Wingcrafter card) {
        super(card);
    }

    @Override
    public Wingcrafter copy() {
        return new Wingcrafter(this);
    }
}
