
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SalvagingStation extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature artifact card with mana value 1 or less from your graveyard");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }
    
    public SalvagingStation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {tap}: Return target noncreature artifact card with converted mana cost 1 or less from your graveyard to the battlefield.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new TapSourceCost());
        secondAbility.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(secondAbility);            
        // Whenever a creature dies, you may untap Salvaging Station.
        this.addAbility(new DiesCreatureTriggeredAbility(new UntapSourceEffect(), true));
    }

    private SalvagingStation(final SalvagingStation card) {
        super(card);
    }

    @Override
    public SalvagingStation copy() {
        return new SalvagingStation(this);
    }
}
