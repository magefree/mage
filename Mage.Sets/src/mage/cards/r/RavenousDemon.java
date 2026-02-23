package mage.cards.r;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author intimidatingant
 */
public final class RavenousDemon extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "Human");

    public RavenousDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "{3}{B}{B}",
                "Archdemon of Greed",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "B"
        );

        // Ravenous Demon
        this.getLeftHalfCard().setPT(4, 4);

        // Sacrifice a Human: Transform Ravenous Demon. Activate this ability only any time you could cast a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new SacrificeTargetCost(filter)));

        // Archdemon of Greed
        this.getRightHalfCard().setPT(9, 9);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                null, new TapSourceEffect(), new SacrificeTargetCost(filter), false
        ).addOtherwiseEffect(new DamageControllerEffect(9))
                .setText("sacrifice a Human. If you can't, tap {this} and it deals 9 damage to you")));
    }

    private RavenousDemon(final RavenousDemon card) {
        super(card);
    }

    @Override
    public RavenousDemon copy() {
        return new RavenousDemon(this);
    }
}
