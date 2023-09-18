package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NikaraLairScavenger extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(CounterAnyPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public NikaraLairScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Yannik, Scavenging Sentinel
        this.addAbility(new PartnerWithAbility("Yannik, Scavenging Sentinel"));

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever another creature you control leaves the battlefield, if it had one or more counters on it, you draw a card and lose 1 life.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(new DrawCardSourceControllerEffect(1)
                .setText("if it had one or more counters on it, you draw a card"), filter);
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private NikaraLairScavenger(final NikaraLairScavenger card) {
        super(card);
    }

    @Override
    public NikaraLairScavenger copy() {
        return new NikaraLairScavenger(this);
    }
}
