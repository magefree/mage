package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class KindlyStranger extends TransformingDoubleFacedCard {

    public KindlyStranger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN}, "{2}{B}",
                "Demon-Possessed Witch",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "B");

        // Kindly Stranger
        this.getLeftHalfCard().setPT(2, 3);

        // Delirium — {2}{B}: Transform Kindly Stranger. Activate only if there are four or more card types among cards in your graveyard.
        this.getLeftHalfCard().addAbility(new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{B}"), DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // Demon-Possessed Witch
        this.getRightHalfCard().setPT(4, 3);

        // When this creature transforms into Demon-Possessed Witch, you may destroy target creature.
        Ability ability = new TransformIntoSourceTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(ability);
    }

    private KindlyStranger(final KindlyStranger card) {
        super(card);
    }

    @Override
    public KindlyStranger copy() {
        return new KindlyStranger(this);
    }
}
