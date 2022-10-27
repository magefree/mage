package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author North
 */
public final class SilvergillAdept extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Merfolk card from your hand");

    static {
        filter.add(SubType.MERFOLK.getPredicate());
    }

    public SilvergillAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As an additional cost to cast Silvergill Adept, reveal a Merfolk card from your hand or pay {3}.
        this.getSpellAbility().addCost(new OrCost(
                "reveal a Merfolk card from your hand or pay {3}", new RevealTargetFromHandCost(new TargetCardInHand(filter)),
                new GenericManaCost(3)
        ));

        // When Silvergill Adept enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private SilvergillAdept(final SilvergillAdept card) {
        super(card);
    }

    @Override
    public SilvergillAdept copy() {
        return new SilvergillAdept(this);
    }
}
