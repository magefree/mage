
package mage.cards.d;

import java.util.UUID;
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
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class DespoilerOfSouls extends CardImpl {
    
    public DespoilerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Despoiler of Souls can't block.
        this.addAbility(new CantBlockAbility());
        
        // {B}{B}, Exile two other creature cards from your graveyard: Return Despoiler of Souls from your graveyard to the battlefield.
        FilterCard filter = new FilterCreatureCard("two other creature cards from your graveyard");
        filter.add(Predicates.not(new CardIdPredicate(this.getId())));
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false), new ManaCostsImpl("{B}{B}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, filter)));
        this.addAbility(ability);
    }

    private DespoilerOfSouls(final DespoilerOfSouls card) {
        super(card);
    }

    @Override
    public DespoilerOfSouls copy() {
        return new DespoilerOfSouls(this);
    }
}
