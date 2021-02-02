

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.ZuberasDiedDynamicValue;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.watchers.common.ZuberasDiedWatcher;

/**
 * @author Loki
 */
public final class AshenSkinZubera extends CardImpl {

    public AshenSkinZubera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZUBERA);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new DiesSourceTriggeredAbility(new DiscardTargetEffect(ZuberasDiedDynamicValue.instance));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new ZuberasDiedWatcher());
    }

    private AshenSkinZubera(final AshenSkinZubera card) {
        super(card);
    }

    @Override
    public AshenSkinZubera copy() {
        return new AshenSkinZubera(this);
    }

}

