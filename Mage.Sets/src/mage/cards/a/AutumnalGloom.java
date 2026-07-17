package mage.cards.a;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AutumnalGloom extends TransformingDoubleFacedCard {

    public AutumnalGloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{2}{G}",
                "Ancient of the Equinox",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.TREEFOLK}, "G");
        this.getRightHalfCard().setPT(4, 4);

        // {B}: Put the top card of your library into your graveyard.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(1), new ManaCostsImpl<>("{B}")));

        // <i>Delirium</i> &mdash; At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect(), false, DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // Ancient of the Equinox
        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());
        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());
    }

    private AutumnalGloom(final AutumnalGloom card) {
        super(card);
    }

    @Override
    public AutumnalGloom copy() {
        return new AutumnalGloom(this);
    }
}
