package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WhiteWidowFreeAgent extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or enchantment card from your graveyard");

    static {
        filter.add(
            Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
            )
        );
    }

    public WhiteWidowFreeAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When White Widow enters, choose one --
        // * Put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));

        // * Return target artifact or enchantment card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private WhiteWidowFreeAgent(final WhiteWidowFreeAgent card) {
        super(card);
    }

    @Override
    public WhiteWidowFreeAgent copy() {
        return new WhiteWidowFreeAgent(this);
    }
}
