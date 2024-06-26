
package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.LookAtOpponentFaceDownCreaturesAnyTimeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KeeperOfTheLens extends CardImpl {

    public KeeperOfTheLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // You may look at face-down creatures you don't control.
        this.addAbility(new SimpleStaticAbility(new LookAtOpponentFaceDownCreaturesAnyTimeEffect()));
    }

    private KeeperOfTheLens(final KeeperOfTheLens card) {
        super(card);
    }

    @Override
    public KeeperOfTheLens copy() {
        return new KeeperOfTheLens(this);
    }
}
