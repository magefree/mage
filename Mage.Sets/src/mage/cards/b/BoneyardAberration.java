package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneyardAberration extends CardImpl {

    public BoneyardAberration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Boneyard Aberration dies, exile it. If you do, conjure three Reassembling Skeleton cards into your graveyard.
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfCostPaid(
                new ConjureCardEffect("Reassembling Skeleton", Zone.GRAVEYARD, 3),
                new ExileSourceFromGraveCost().setText("exile it"), null, false
        )));
    }

    private BoneyardAberration(final BoneyardAberration card) {
        super(card);
    }

    @Override
    public BoneyardAberration copy() {
        return new BoneyardAberration(this);
    }
}
