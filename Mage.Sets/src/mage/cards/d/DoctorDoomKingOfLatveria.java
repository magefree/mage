package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DoctorDoomKingOfLatveria extends CardImpl {

    private static final FilterControlledPermanent filter =
        new FilterControlledPermanent(SubType.VILLAIN);

    public DoctorDoomKingOfLatveria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you discard one or more land cards, each opponent loses 2 life.
        this.addAbility(new DiscardOneOrMoreCardsTriggeredAbility(
            Zone.BATTLEFIELD, new LoseLifeOpponentsEffect(2), false, StaticFilters.FILTER_CARD_LANDS
        ));

        // At the beginning of combat on your turn, target Villain you control gains menace until end of turn. It connives.
        Ability ability = new BeginningOfCombatTriggeredAbility(
            new GainAbilityTargetEffect(new MenaceAbility(false))
        );
        ability.addEffect(new ConniveTargetEffect().setText("it connives"));
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private DoctorDoomKingOfLatveria(final DoctorDoomKingOfLatveria card) {
        super(card);
    }

    @Override
    public DoctorDoomKingOfLatveria copy() {
        return new DoctorDoomKingOfLatveria(this);
    }
}
