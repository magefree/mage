package mage.cards.c;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Stravant
 */
public final class CutRibbons extends SplitCard {

    public CutRibbons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{1}{R}", "{X}{B}{B}", SpellAbilityType.SPLIT_AFTERMATH);

        // Cut
        // Cut deals 4 damage to target creature.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(4).setText("Cut deals 4 damage to target creature"));

        // to
        // Ribbons
        // Each opponent loses X life.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addEffect(new LoseLifeOpponentsEffect(ManacostVariableValue.instance));

    }

    public CutRibbons(final CutRibbons card) {
        super(card);
    }

    @Override
    public CutRibbons copy() {
        return new CutRibbons(this);
    }
}
