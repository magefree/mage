package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ScrapheapScrounger extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("another creature card");

    static {
        filter.add(new AnotherCardPredicate());
    }

    public ScrapheapScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Scrapheap Scrounger can't block.
        this.addAbility(new CantBlockAbility());

        // {1}{B}, Exile another creature card from your graveyard: Return Scrapheap Scrounger from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false, false), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        this.addAbility(ability);
    }

    private ScrapheapScrounger(final ScrapheapScrounger card) {
        super(card);
    }

    @Override
    public ScrapheapScrounger copy() {
        return new ScrapheapScrounger(this);
    }
}
