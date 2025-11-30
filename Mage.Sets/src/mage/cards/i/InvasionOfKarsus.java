package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfKarsus extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker");

    public InvasionOfKarsus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{R}{R}",
                "Refraction Elemental",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "R"
        );

        // Invasion of Karsus
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When this Siege enters, it deals 3 damage to each creature and each planeswalker.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(3, "it", filter)));

        // Refraction Elemental
        this.getRightHalfCard().setPT(4, 4);

        // Ward—Pay 2 life.
        this.getRightHalfCard().addAbility(new WardAbility(new PayLifeCost(2), false));

        // Whenever you cast a spell, Refraction Elemental deals 2 damage to each opponent.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), false
        ));
    }

    private InvasionOfKarsus(final InvasionOfKarsus card) {
        super(card);
    }

    @Override
    public InvasionOfKarsus copy() {
        return new InvasionOfKarsus(this);
    }
}
