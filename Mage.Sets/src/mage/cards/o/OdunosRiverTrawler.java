
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class OdunosRiverTrawler extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantment creature card from your graveyard");
    
    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
    }
    
    public OdunosRiverTrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Odunos River Trawler enters the battlefield, return target enchantment creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));        
        this.addAbility(ability);
        // {W}, Sacrifice Odunos River Trawler: Return target enchantment creature card from your graveyard to your hand.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));        
        this.addAbility(ability);
    }

    private OdunosRiverTrawler(final OdunosRiverTrawler card) {
        super(card);
    }

    @Override
    public OdunosRiverTrawler copy() {
        return new OdunosRiverTrawler(this);
    }
}
