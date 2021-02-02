package mage.cards.m;

import mage.abilities.condition.common.ProwlCostWasPaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.ProwlCostWasPaidHint;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MorselTheft extends CardImpl {

    public MorselTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{2}{B}{B}");
        this.subtype.add(SubType.ROGUE);

        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));

        // Target player loses 3 life and you gain 3 life. If Morsel Theft's prowl cost was paid, draw a card.
        getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        Effect effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), ProwlCostWasPaidCondition.instance));
        getSpellAbility().addHint(ProwlCostWasPaidHint.instance);

    }

    private MorselTheft(final MorselTheft card) {
        super(card);
    }

    @Override
    public MorselTheft copy() {
        return new MorselTheft(this);
    }
}
