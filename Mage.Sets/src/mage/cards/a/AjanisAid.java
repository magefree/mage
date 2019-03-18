
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceToYouEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 */
public final class AjanisAid extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ajani, Valiant Protector");

    static {
        filter.add(new NamePredicate("Ajani, Valiant Protector"));
    }

    public AjanisAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // When Ajani's Aid enters the battlefield, you may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it,
        // and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));

        // Sacrifice Ajani's Aid: Prevent all combat damage a creature of your choice would deal this turn.
        Effect effect = new PreventNextDamageFromChosenSourceToYouEffect(Duration.EndOfTurn, new FilterCreaturePermanent("creature of your choice"), true);
        effect.setText("Prevent all combat damage a creature of your choice would deal this turn");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new SacrificeSourceCost()));
    }

    private AjanisAid(final AjanisAid card) {
        super(card);
    }

    @Override
    public AjanisAid copy() {
        return new AjanisAid(this);
    }
}
