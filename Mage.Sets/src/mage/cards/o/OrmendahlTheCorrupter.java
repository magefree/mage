package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrmendahlTheCorrupter extends CardImpl {

    public OrmendahlTheCorrupter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.color.setBlack(true);
        this.transformable = true;
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Sacrifice another creature: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(
                        StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
                ))
        ));
    }

    private OrmendahlTheCorrupter(final OrmendahlTheCorrupter card) {
        super(card);
    }

    @Override
    public OrmendahlTheCorrupter copy() {
        return new OrmendahlTheCorrupter(this);
    }
}
