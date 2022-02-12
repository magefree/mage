package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes & L_J
 */
public final class FlintGolem extends CardImpl {

    public FlintGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Flint Golem becomes blocked, defending player mills three cards
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new MillCardsTargetEffect(3).setText("defending player mills three cards"),
                false, true));
    }

    private FlintGolem(final FlintGolem card) {
        super(card);
    }

    @Override
    public FlintGolem copy() {
        return new FlintGolem(this);
    }
}
