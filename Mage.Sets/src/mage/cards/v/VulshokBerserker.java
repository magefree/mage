

package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class VulshokBerserker extends CardImpl {

    public VulshokBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
    }

    private VulshokBerserker(final VulshokBerserker card) {
        super(card);
    }

    @Override
    public VulshokBerserker copy() {
        return new VulshokBerserker(this);
    }

}
