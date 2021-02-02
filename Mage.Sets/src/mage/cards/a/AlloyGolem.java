
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class AlloyGolem extends CardImpl {

    public AlloyGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Alloy Golem enters the battlefield, choose a color.
        // Alloy Golem is the chosen color.
        this.addAbility(new EntersBattlefieldAbility(new BecomesColorSourceEffect(Duration.WhileOnBattlefield),
            null, "As {this} enters the battlefield, choose a color.\n{this} is the chosen color.", ""));
    }

    private AlloyGolem(final AlloyGolem card) {
        super(card);
    }

    @Override
    public AlloyGolem copy() {
        return new AlloyGolem(this);
    }
}
