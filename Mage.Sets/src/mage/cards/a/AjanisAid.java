
package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByChosenSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AjanisAid extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ajani, Valiant Protector");
    private static final FilterSource filterSource = new FilterSource("creature of your choice");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new NamePredicate("Ajani, Valiant Protector"));
    }

    public AjanisAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}");

        // When Ajani's Aid enters the battlefield, you may search your library and/or graveyard for a card named Ajani, Valiant Protector, reveal it,
        // and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));

        // Sacrifice Ajani's Aid: Prevent all combat damage a creature of your choice would deal this turn.
        Effect effect = new PreventDamageByChosenSourceEffect(filterSource, true);
        this.addAbility(new SimpleActivatedAbility(effect, new SacrificeSourceCost()));
    }

    private AjanisAid(final AjanisAid card) {
        super(card);
    }

    @Override
    public AjanisAid copy() {
        return new AjanisAid(this);
    }
}
