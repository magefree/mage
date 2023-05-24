
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.VampireToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BloodlineKeeper extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "you control five or more Vampires");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent(SubType.VAMPIRE, "Vampire creatures");

    public BloodlineKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "{2}{B}{B}",
                "Lord of Lineage",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );
        this.getLeftHalfCard().setPT(3, 3);
        this.getRightHalfCard().setPT(5, 5);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // {T}: Create a 2/2 black Vampire creature token with flying.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new VampireToken()), new TapSourceCost()
        ));

        // {B}: Transform Bloodline Keeper. Activate this ability only if you control five or more Vampires.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{B}"),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4)
        );
        this.getLeftHalfCard().addAbility(ability);

        // Lord of Lineage
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Other Vampire creatures you control get +2/+2.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter2, true
        )));

        // {tap}: Create a 2/2 black Vampire creature token with flying.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new VampireToken()), new TapSourceCost()
        ));
    }

    private BloodlineKeeper(final BloodlineKeeper card) {
        super(card);
    }

    @Override
    public BloodlineKeeper copy() {
        return new BloodlineKeeper(this);
    }
}
