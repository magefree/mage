
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ButcherOfMalakir extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ButcherOfMalakir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Butcher of Malakir or another creature you control dies, each opponent sacrifices a creature.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT), false, filter));
    }

    private ButcherOfMalakir(final ButcherOfMalakir card) {
        super(card);
    }

    @Override
    public ButcherOfMalakir copy() {
        return new ButcherOfMalakir(this);
    }
}
