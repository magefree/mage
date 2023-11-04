package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author TheElk801
 */
public final class SuleimansLegacy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Djinns and Efreets");

    static {
        filter.add(Predicates.or(
                SubType.DJINN.getPredicate(),
                SubType.EFREET.getPredicate()
        ));
    }

    public SuleimansLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        // When Suleiman's Legacy enters the battlefield, destroy all Djinns and Efreets. They can't be regenerated.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filter, true)));

        // Whenever a Djinn or Efreet enters the battlefield, destroy it. It can't be regenerated.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect("destroy it. It can't be regenerated.", true),
                filter, false, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("Whenever a Djinn or Efreet enters the battlefield, "));
    }

    private SuleimansLegacy(final SuleimansLegacy card) {
        super(card);
    }

    @Override
    public SuleimansLegacy copy() {
        return new SuleimansLegacy(this);
    }
}
