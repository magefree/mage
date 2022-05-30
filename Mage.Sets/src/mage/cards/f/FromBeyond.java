
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.permanent.token.EldraziScionToken;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class FromBeyond extends CardImpl {

    private static final FilterCard filter = new FilterCard("Eldrazi card");

    static {
        filter.add(SubType.ELDRAZI.getPredicate());
    }

    public FromBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // At the beginning of your upkeep, create a 1/1 colorless Eldrazi Scion creature token. It has "Sacrifice this creature: Add {C}."
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new EldraziScionToken()), TargetController.YOU, false));

        // {1}{G}, Sacrifice From Beyond: Search your library for an Eldrazi card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FromBeyond(final FromBeyond card) {
        super(card);
    }

    @Override
    public FromBeyond copy() {
        return new FromBeyond(this);
    }
}
