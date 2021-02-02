
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class WojekHalberdiers extends CardImpl {

    public WojekHalberdiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Battalion - Whenever Wojek Halberdiers and at least two other creatures attack, Wojek Halberdiers gains first strike until end of turn.
        this.addAbility(new BattalionAbility(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)));
    }

    private WojekHalberdiers(final WojekHalberdiers card) {
        super(card);
    }

    @Override
    public WojekHalberdiers copy() {
        return new WojekHalberdiers(this);
    }
}
