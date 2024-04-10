package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KirriTalentedSprout extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Plants and Treefolk");
    private static final FilterCard filter2 = new FilterCard("Plant, Treefolk, or land card from your graveyard");

    static {
        filter.add(Predicates.or(
                SubType.PLANT.getPredicate(),
                SubType.TREEFOLK.getPredicate()
        ));
        filter2.add(Predicates.or(
                SubType.PLANT.getPredicate(),
                SubType.TREEFOLK.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public KirriTalentedSprout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Other Plants and Treefolk you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileControlled, filter, true
        )));

        // At the beginning of your postcombat main phase, return target Plant, Treefolk, or land card from your graveyard to your hand.
        Ability ability = new BeginningOfPostCombatMainTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private KirriTalentedSprout(final KirriTalentedSprout card) {
        super(card);
    }

    @Override
    public KirriTalentedSprout copy() {
        return new KirriTalentedSprout(this);
    }
}
