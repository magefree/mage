package mage.cards.j;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author notshauna
 */
public final class JulianBashirFrontierDoctor extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value X or less from your graveyard"
    );

    static {
        filter.add(JulianBashirFrontierDoctorPredicate.instance);
    }

    public JulianBashirFrontierDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Seek out — Whenever you draw your second card each turn, put a +1/+1 counter on Julian Bashir.
        Ability drawabiility = new DrawNthCardTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withFlavorWord("Seek out");
        this.addAbility(drawabiility);

        // Whenever Julian Bashir attacks, return target creature card with mana value X or less from your graveyard
        // to the battlefield, where X is Julian Bashir’s power.
        Ability reanimationability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target creature card with mana value X or less from your graveyard " +
                        "to the battlefield, where X is Julian Bashir’s power"));
        reanimationability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(reanimationability);
    }

    private JulianBashirFrontierDoctor(final JulianBashirFrontierDoctor card) {
        super(card);
    }

    @Override
    public JulianBashirFrontierDoctor copy() {
        return new JulianBashirFrontierDoctor(this);
    }
}

enum JulianBashirFrontierDoctorPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(p -> input.getObject().getManaValue() <= p.getValue())
                .orElse(false);
    }
}