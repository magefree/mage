package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KittKantoMayhemDiva extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature controlled by the active player");

    static {
        filter.add(TargetController.ACTIVE.getControllerPredicate());
    }

    public KittKantoMayhemDiva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Kitt Kanto enters the battlefield, create a 1/1 green and white Citizen creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CitizenGreenWhiteToken())));

        // At the beginning of combat on each player's turn, you may tap two untapped creatures you control. When you do, target creature that player controls gets +2/+2 and gains trample until end of turn. Goad that creature.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(2, 2)
                        .setText("target creature that player controls gets +2/+2"),
                false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                "and gains trample until end of turn"
        ));
        ability.addEffect(new GoadTargetEffect().setText("Goad that creature"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability,
                new TapTargetCost(new TargetControlledPermanent(
                        2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                )), "Tap two untapped creatures you control?"
        ), TargetController.EACH_PLAYER, false));
    }

    private KittKantoMayhemDiva(final KittKantoMayhemDiva card) {
        super(card);
    }

    @Override
    public KittKantoMayhemDiva copy() {
        return new KittKantoMayhemDiva(this);
    }
}
