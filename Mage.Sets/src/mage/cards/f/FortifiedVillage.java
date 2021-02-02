
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author fireshoes
 */
public final class FortifiedVillage extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Forest or Plains card from your hand");

    static {
        filter.add(Predicates.or(SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()));
    }

    public FortifiedVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Fortified Village enters the battlefield, you may reveal a Forest or Plains card from your hand. If you don't, Fortified Village enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal a Forest or Plains card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private FortifiedVillage(final FortifiedVillage card) {
        super(card);
    }

    @Override
    public FortifiedVillage copy() {
        return new FortifiedVillage(this);
    }
}
