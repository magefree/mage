package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class TempestEfreet extends CardImpl {

    public TempestEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}{R}");
        
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Remove Tempest Efreet from your deck before playing if you're not playing for ante.
        // {T}, Sacrifice Tempest Efreet: Target opponent may pay 10 life. If that player doesn’t, they reveal a card at random from their hand. Exchange ownership of the revealed card and Tempest Efreet. Put the revealed card into your hand and Tempest Efreet from anywhere into that player’s graveyard. This change in ownership is permanent.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Tempest Efreet from your deck before playing if" +
                " you're not playing for ante.\n{T}, Sacrifice Tempest Efreet: Target opponent may pay 10 life. If " +
                "that player doesn’t, they reveal a card at random from their hand. Exchange ownership of the" +
                " revealed card and Tempest Efreet. Put the revealed card into your hand and Tempest Efreet from " +
                "anywhere into that player’s graveyard. This change in ownership is permanent."));
    }

    public TempestEfreet(final TempestEfreet card) {
        super(card);
    }

    @Override
    public TempestEfreet copy() {
        return new TempestEfreet(this);
    }
}
