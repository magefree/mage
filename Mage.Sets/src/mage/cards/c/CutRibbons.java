package mage.cards.c;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Stravant
 */
public class CutRibbons extends SplitCard {
    public CutRibbons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}","{X}{B}{B}",false);

        // Cut
        // Cut deals 4 damage to target creature.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(4));

        // to

        // Feed
        // Draw a card for each creature you control with power 3 or greater
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility());
        getRightHalfCard().getSpellAbility().addEffect(new LoseLifeOpponentsEffect(new ManacostVariableValue()));

    }

    public CutRibbons(final CutRibbons card) {
        super(card);
    }

    @Override
    public CutRibbons copy() {
        return new CutRibbons(this);
    }
}