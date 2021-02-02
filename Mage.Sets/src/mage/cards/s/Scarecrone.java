
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class Scarecrone extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact creature card from your graveyard");
    private static final FilterControlledCreaturePermanent filterScarecrow = new FilterControlledCreaturePermanent("Scarecrow");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filterScarecrow.add(SubType.SCARECROW.getPredicate());
    }

    public Scarecrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a Scarecrow: Draw a card.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filterScarecrow, false)));
        this.addAbility(firstAbility);

        // {4}, {T}: Return target artifact creature card from your graveyard to the battlefield.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new GenericManaCost(4));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(secondAbility);
    }

    private Scarecrone(final Scarecrone card) {
        super(card);
    }

    @Override
    public Scarecrone copy() {
        return new Scarecrone(this);
    }
}
