package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfDominaria extends TransformingDoubleFacedCard {

    public InvasionOfDominaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{W}",
                "Serra Faithkeeper",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL}, "W"
        );

        // Invasion of Dominaria
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Dominaria enters the battlefield, you gain 4 life and draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.getLeftHalfCard().addAbility(ability);

        // Serra Faithkeeper
        this.getRightHalfCard().setPT(4, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());
    }

    private InvasionOfDominaria(final InvasionOfDominaria card) {
        super(card);
    }

    @Override
    public InvasionOfDominaria copy() {
        return new InvasionOfDominaria(this);
    }
}
