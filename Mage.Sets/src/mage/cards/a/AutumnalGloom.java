package mage.cards.a;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AutumnalGloom extends CardImpl {

    public AutumnalGloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.secondSideCardClazz = mage.cards.a.AncientOfTheEquinox.class;

        // {B}: Put the top card of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(1), new ManaCostsImpl<>("{B}")));

        // <i>Delirium</i> &mdash; At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect(), false, DeliriumCondition.instance
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private AutumnalGloom(final AutumnalGloom card) {
        super(card);
    }

    @Override
    public AutumnalGloom copy() {
        return new AutumnalGloom(this);
    }
}
