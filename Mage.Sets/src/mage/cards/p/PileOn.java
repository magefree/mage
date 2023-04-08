package mage.cards.p;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PileOn extends CardImpl {

    public PileOn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private PileOn(final PileOn card) {
        super(card);
    }

    @Override
    public PileOn copy() {
        return new PileOn(this);
    }
}
