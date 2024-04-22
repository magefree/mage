package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class AltarOfTheBrood extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AltarOfTheBrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever another permanent enters the battlefield under your control, each opponent mills a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
                new MillCardsEachPlayerEffect(1, TargetController.OPPONENT), filter, false));
    }

    private AltarOfTheBrood(final AltarOfTheBrood card) {
        super(card);
    }

    @Override
    public AltarOfTheBrood copy() {
        return new AltarOfTheBrood(this);
    }
}
