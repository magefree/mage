
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class ChokedEstuary extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Island or Swamp card from your hand");

    static {
        filter.add(Predicates.or(SubType.ISLAND.getPredicate(),
                SubType.SWAMP.getPredicate()));
    }

    public ChokedEstuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Choked Estuary enters the battlefield, you may reveal an Island or Swamp card from your hand. If you don't, Choked Estuary enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))),
                "you may reveal an Island or Swamp card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private ChokedEstuary(final ChokedEstuary card) {
        super(card);
    }

    @Override
    public ChokedEstuary copy() {
        return new ChokedEstuary(this);
    }
}
