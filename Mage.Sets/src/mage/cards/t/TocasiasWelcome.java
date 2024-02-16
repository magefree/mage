package mage.cards.t;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TocasiasWelcome extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TocasiasWelcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever one or more creatures with mana value 3 or less enter the battlefield under your control, draw a card. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ).setTriggersOnceEachTurn(true).setTriggerPhrase("Whenever one or more creatures with mana value 3 " +
                "or less enter the battlefield under your control, "));
    }

    private TocasiasWelcome(final TocasiasWelcome card) {
        super(card);
    }

    @Override
    public TocasiasWelcome copy() {
        return new TocasiasWelcome(this);
    }
}
