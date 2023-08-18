
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevouredCreaturesCount;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TarFiend extends CardImpl {

    public TarFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Devour 2 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(2));

        // When Tar Fiend enters the battlefield, target player discards a card for each creature it devoured.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(new DevouredCreaturesCount()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private TarFiend(final TarFiend card) {
        super(card);
    }

    @Override
    public TarFiend copy() {
        return new TarFiend(this);
    }
}
