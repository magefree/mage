package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IchTekikSalvageSplicer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOLEM);

    public IchTekikSalvageSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Ich-Tekik, Salvage Splicer enters the battlefield, create a 3/3 colorless Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianGolemToken())));

        // Whenever an artifact is put into a graveyard from the battlefield, put a +1/+1 counter on Ich-Tekik and a +1/+1 counter on each Golem you control.
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, false, false
        );
        ability.addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), filter
        ).setText("and a +1/+1 counter on each Golem you control"));
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private IchTekikSalvageSplicer(final IchTekikSalvageSplicer card) {
        super(card);
    }

    @Override
    public IchTekikSalvageSplicer copy() {
        return new IchTekikSalvageSplicer(this);
    }
}
