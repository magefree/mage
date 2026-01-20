package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.common.BeholdAndExileCost;
import mage.abilities.effects.common.ReturnExiledCardToHandEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionsOfTheShoal extends CardImpl {

    public ChampionsOfTheShoal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, behold a Merfolk and exile it.
        this.getSpellAbility().addCost(new BeholdAndExileCost(SubType.MERFOLK));

        // Whenever this creature enters or becomes tapped, tap up to one target creature and put a stun counter on it.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(), false,
                "Whenever this creature enters or becomes tapped, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTappedTriggeredAbility(null, false)
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("and put a stun counter on it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // When this creature leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnExiledCardToHandEffect()));
    }

    private ChampionsOfTheShoal(final ChampionsOfTheShoal card) {
        super(card);
    }

    @Override
    public ChampionsOfTheShoal copy() {
        return new ChampionsOfTheShoal(this);
    }
}
