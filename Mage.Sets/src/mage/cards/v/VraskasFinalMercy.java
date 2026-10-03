package mage.cards.v;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class VraskasFinalMercy extends CardImpl {

    public VraskasFinalMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{B}");

        // Choose one --
        // * You lose 2 life. Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // * You lose 2 life. Empower Jace 6.
        this.getSpellAbility().addMode(new Mode(new LoseLifeSourceControllerEffect(2))
            .addEffect(new EmpowerJaceEffect(6))
        );
    }

    private VraskasFinalMercy(final VraskasFinalMercy card) {
        super(card);
    }

    @Override
    public VraskasFinalMercy copy() {
        return new VraskasFinalMercy(this);
    }
}
