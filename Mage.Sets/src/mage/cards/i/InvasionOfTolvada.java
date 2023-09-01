package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTolvada extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterPermanentCard("nonbattle permanent card from your graveyard");

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature tokens");

    static {
        filter.add(Predicates.not(CardType.BATTLE.getPredicate()));
        filter2.add(TokenPredicate.TRUE);
    }

    public InvasionOfTolvada(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{W}{B}",
                "The Broken Sky",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "BW"
        );
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Tolvada enters the battlefield, return target nonbattle permanent card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.getLeftHalfCard().addAbility(ability);

        // The Broken Sky
        // Creature tokens you control get +1/+0 and have lifelink.
        ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter2
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        ).setText("and have lifelink"));
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of your end step, create a 1/1 white and black Spirit creature token with flying.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new WhiteBlackSpiritToken()),
                TargetController.YOU, false
        ));
    }

    private InvasionOfTolvada(final InvasionOfTolvada card) {
        super(card);
    }

    @Override
    public InvasionOfTolvada copy() {
        return new InvasionOfTolvada(this);
    }
}
