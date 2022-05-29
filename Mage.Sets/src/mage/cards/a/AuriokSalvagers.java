
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class AuriokSalvagers extends CardImpl {

    private static final FilterArtifactCard filter = new FilterArtifactCard("artifact card with mana value 1 or less from your graveyard");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }
    
    public AuriokSalvagers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {1}{W}: Return target artifact card with converted mana cost 1 or less from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(),new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    private AuriokSalvagers(final AuriokSalvagers card) {
        super(card);
    }

    @Override
    public AuriokSalvagers copy() {
        return new AuriokSalvagers(this);
    }
}
