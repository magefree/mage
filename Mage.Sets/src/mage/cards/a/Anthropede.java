package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Anthropede extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ROOM, "Room");

    public Anthropede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Anthropede enters, you may discard a card or pay {2}. When you do, destroy target Room.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability,
                new OrCost("discard a card or pay {2}", new DiscardCardCost(), new GenericManaCost(2)),
                "Discard a card or pay {2}?"
        )));
    }

    private Anthropede(final Anthropede card) {
        super(card);
    }

    @Override
    public Anthropede copy() {
        return new Anthropede(this);
    }
}
