package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class AutumnalGloom extends CardImpl {

    public AutumnalGloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.secondSideCardClazz = AncientOfTheEquinox.class;

        // {B}: Put the top card of your library into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MillCardsControllerEffect(1), new ManaCostsImpl("{B}")));

        // <i>Delirium</i> &mdash; At the beginning of your end step, if there are four or more card types among cards in your graveyard, transform Autumnal Gloom.
        this.addAbility(new TransformAbility());
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), TargetController.YOU, DeliriumCondition.instance, false);
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);
    }

    private AutumnalGloom(final AutumnalGloom card) {
        super(card);
    }

    @Override
    public AutumnalGloom copy() {
        return new AutumnalGloom(this);
    }
}
