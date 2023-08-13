
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class TarPitWarrior extends CardImpl {

    public TarPitWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Tar Pit Warrior becomes the target of a spell or ability, sacrifice it.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
    }

    private TarPitWarrior(final TarPitWarrior card) {
        super(card);
    }

    @Override
    public TarPitWarrior copy() {
        return new TarPitWarrior(this);
    }
}
