package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MiaraThornOfTheGlade extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELF, "Elf you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public MiaraThornOfTheGlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Miara, Thorn of the Glade or another Elf you control dies, you may pay {1} and 1 life. If you do, draw a card.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new CompositeCost(
                                new GenericManaCost(1),
                                new PayLifeCost(1),
                                "{1} and 1 life"
                        )
                ), false, filter
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private MiaraThornOfTheGlade(final MiaraThornOfTheGlade card) {
        super(card);
    }

    @Override
    public MiaraThornOfTheGlade copy() {
        return new MiaraThornOfTheGlade(this);
    }
}
