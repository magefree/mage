package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrBattleTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKamigawa extends TransformingDoubleFacedCard {

    public InvasionOfKamigawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{U}",
                "Rooftop Saboteurs",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MOONFOLK, SubType.NINJA}, "U"
        );

        // Invasion of Kamigawa
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());

        // When Invasion of Kamigawa enters the battlefield, tap target artifact or creature an opponent controls and put a stun counter on it.
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getLeftHalfCard().addAbility(ability);

        // Rooftop Saboteurs
        this.getRightHalfCard().setPT(2, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Rooftop Saboteurs deals combat damage to a player or battle, draw a card.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerOrBattleTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false));
    }

    private InvasionOfKamigawa(final InvasionOfKamigawa card) {
        super(card);
    }

    @Override
    public InvasionOfKamigawa copy() {
        return new InvasionOfKamigawa(this);
    }
}
