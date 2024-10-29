package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GildedScuttler extends CardImpl {

    public GildedScuttler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Gilded Scuttler can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // When Gilded Scuttler enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private GildedScuttler(final GildedScuttler card) {
        super(card);
    }

    @Override
    public GildedScuttler copy() {
        return new GildedScuttler(this);
    }
}
