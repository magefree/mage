
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author LevelX2
 */
public final class PitilessPlunderer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PitilessPlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever another creature you control dies, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false, filter));
    }

    private PitilessPlunderer(final PitilessPlunderer card) {
        super(card);
    }

    @Override
    public PitilessPlunderer copy() {
        return new PitilessPlunderer(this);
    }
}
