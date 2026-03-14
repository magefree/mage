package mage.cards.t;

import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.GnomeSoldierStarStarToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ThousandMoonsSmithy extends TransformingDoubleFacedCard {

    public static final FilterControlledPermanent filter =
            new FilterControlledPermanent("untapped artifacts and/or creatures you control");

    private static final FilterSpell filter2 = new FilterSpell("an artifact or creature spell");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public ThousandMoonsSmithy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{W}{W}",
                "Barracks of the Thousand",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.LAND}, new SubType[]{}, ""
        );

        // Thousand Moons Smithy
        // When Thousand Moons Smithy enters the battlefield, create a white Gnome Soldier artifact creature token with "This creature's power and toughness are each equal to the number of artifacts and/or creatures you control."
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeSoldierStarStarToken())));

        // At the beginning of your precombat main phase, you may tap five untapped artifacts and/or creatures you control. If you do, transform Thousand Moons Smithy.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(
                        new TransformSourceEffect(),
                        new TapTargetCost(new TargetControlledPermanent(5, filter))
                )
        ));

        // Barracks of the Thousand
        // (Transforms from Thousand Moons Smithy.)

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());

        // Whenever you cast an artifact or creature spell using mana produced by Barracks of the Thousand, create a white Gnome Soldier artifact creature token with "This creature's power and toughness are each equal to the number of artifacts and/or creatures you control."
        this.getRightHalfCard().addAbility(new CastSpellPaidBySourceTriggeredAbility(
                new CreateTokenEffect(new GnomeSoldierStarStarToken()),
                filter2, false
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
