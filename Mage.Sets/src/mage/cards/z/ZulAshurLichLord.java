package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class ZulAshurLichLord extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("Zombie creature card");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public ZulAshurLichLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward--Pay 2 life.
        this.addAbility(new WardAbility(new PayLifeCost(2), false));

        // {T}: You may cast target Zombie creature card from your graveyard this turn.
        Ability ability = new SimpleActivatedAbility(
                new PlayFromNotOwnHandZoneTargetEffect(Zone.GRAVEYARD, TargetController.YOU, Duration.EndOfTurn, false, true)
                        .setText("you may cast target Zombie creature card from your graveyard this turn"),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ZulAshurLichLord(final ZulAshurLichLord card) {
        super(card);
    }

    @Override
    public ZulAshurLichLord copy() {
        return new ZulAshurLichLord(this);
    }
}
