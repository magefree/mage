package mage.cards.t;

import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.GnomeSoldierStarStarToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThousandMoonsSmithy extends CardImpl {

    public static final FilterControlledPermanent filter =
            new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public ThousandMoonsSmithy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{W}");
        this.secondSideCardClazz = mage.cards.b.BarracksOfTheThousand.class;

        this.supertype.add(SuperType.LEGENDARY);

        // When Thousand Moons Smithy enters the battlefield, create a white Gnome Soldier artifact creature token with "This creature's power and toughness are each equal to the number of artifacts and/or creatures you control."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeSoldierStarStarToken())));

        // At the beginning of your precombat main phase, you may tap five untapped artifacts and/or creatures you control. If you do, transform Thousand Moons Smithy.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DoIfCostPaid(
                        new TransformSourceEffect(),
                        new TapTargetCost(new TargetControlledPermanent(5, filter))
                ),
                TargetController.YOU,
                false
        ));
    }

    private ThousandMoonsSmithy(final ThousandMoonsSmithy card) {
        super(card);
    }

    @Override
    public ThousandMoonsSmithy copy() {
        return new ThousandMoonsSmithy(this);
    }
}
