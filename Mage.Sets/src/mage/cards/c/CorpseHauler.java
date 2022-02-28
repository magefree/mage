
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class CorpseHauler extends CardImpl {

    private static final FilterCard filter = new FilterCard("another target creature card from your graveyard");
    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public CorpseHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{B}, Sacrifice Corpse Hauler: Return another target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CorpseHauler(final CorpseHauler card) {
        super(card);
    }

    @Override
    public CorpseHauler copy() {
        return new CorpseHauler(this);
    }
}
