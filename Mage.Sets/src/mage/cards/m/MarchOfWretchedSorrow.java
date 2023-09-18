package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.costs.costadjusters.ExileCardsFromHandAdjuster;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchOfWretchedSorrow extends CardImpl {

    private static final FilterCard filter = new FilterCard("black cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public MarchOfWretchedSorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}");

        // As an additional cost to cast this spell, you may exile any number of black cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        ExileCardsFromHandAdjuster.addAdjusterAndMessage(this, filter);

        // March of Wretched Sorrow deals X damage to target creature or planeswalker and you gain X life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private MarchOfWretchedSorrow(final MarchOfWretchedSorrow card) {
        super(card);
    }

    @Override
    public MarchOfWretchedSorrow copy() {
        return new MarchOfWretchedSorrow(this);
    }
}
