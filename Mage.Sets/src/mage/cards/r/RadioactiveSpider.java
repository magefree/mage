package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadioactiveSpider extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spider Hero card");

    static {
        filter.add(SubType.SPIDER.getPredicate());
        filter.add(SubType.HERO.getPredicate());
    }

    public RadioactiveSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Fateful Bite -- {2}, Sacrifice this creature: Search your library for a Spider Hero card, reveal it, put it into your hand, then shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), new GenericManaCost(2)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.withFlavorWord("Fateful Bite"));
    }

    private RadioactiveSpider(final RadioactiveSpider card) {
        super(card);
    }

    @Override
    public RadioactiveSpider copy() {
        return new RadioactiveSpider(this);
    }
}
