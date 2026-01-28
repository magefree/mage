package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DoctorDoomKingOfLatveria extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("one or more land cards");
    private static final FilterControlledPermanent filter2 =
        new FilterControlledPermanent(SubType.VILLAIN, "Villain you control");

    public DoctorDoomKingOfLatveria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you discard one or more land cards, each opponent loses 2 life.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
            new LoseLifeOpponentsEffect(2), false, filter
        ));

        // At the beginning of combat on your turn, target Villain you control gains menace until end of turn. It connives.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            new GainAbilityTargetEffect(new MenaceAbility())
                .setText("target Villain you control gains menace until end of turn")
        );
        ability.addEffect(new ConniveTargetEffect().setText("it connives"));
        ability.addTarget(new TargetControlledPermanent(filter2));
    }

    private DoctorDoomKingOfLatveria(final DoctorDoomKingOfLatveria card) {
        super(card);
    }

    @Override
    public DoctorDoomKingOfLatveria copy() {
        return new DoctorDoomKingOfLatveria(this);
    }
}
