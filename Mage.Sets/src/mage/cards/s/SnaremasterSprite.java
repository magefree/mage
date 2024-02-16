package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SnaremasterSprite extends CardImpl {

    public SnaremasterSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Snaremaster Sprite enters the battlefield, you may pay {2}. When you do, tap target creature an opponent controls and put a stun counter on it.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        reflexive.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText(" and put a stun counter on it"));
        reflexive.addTarget(new TargetOpponentsCreaturePermanent());

        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(reflexive, new GenericManaCost(2), "Pay {2}?")
        ));
    }

    private SnaremasterSprite(final SnaremasterSprite card) {
        super(card);
    }

    @Override
    public SnaremasterSprite copy() {
        return new SnaremasterSprite(this);
    }
}
