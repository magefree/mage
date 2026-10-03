package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FateshaperAspirant extends CardImpl {

    private static final FilterCard filter =  new FilterCard("legendary card");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public FateshaperAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, choose one --
        // * Return target legendary card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));

        // * Put a +1/+1 counter on target creature. It gains vigilance and indestructible until end of turn.
        ability.addMode(new Mode(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
            .addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
                .setText("It gains vigilance")
            )
            .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and indestructible until end of turn")
            )
            .addTarget(new TargetCreaturePermanent())
        );
        this.addAbility(ability);
    }

    private FateshaperAspirant(final FateshaperAspirant card) {
        super(card);
    }

    @Override
    public FateshaperAspirant copy() {
        return new FateshaperAspirant(this);
    }
}
