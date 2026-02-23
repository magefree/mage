package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousTome extends TransformingDoubleFacedCard {

    public MysteriousTome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{U}",
                "Chilling Chronicle",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U"
        );

        // Mysterious Tome
        // {2}, {T}: Draw a card. Transform Mysterious Tome.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TransformSourceEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Chilling Chronicle
        // {1}, {T}: Tap target nonland permanent. Transform Chilling Chronicle.
        Ability backAbility = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        backAbility.addCost(new TapSourceCost());
        backAbility.addEffect(new TransformSourceEffect());
        backAbility.addTarget(new TargetNonlandPermanent());
        this.getRightHalfCard().addAbility(backAbility);
    }

    private MysteriousTome(final MysteriousTome card) {
        super(card);
    }

    @Override
    public MysteriousTome copy() {
        return new MysteriousTome(this);
    }
}
