package mage.cards.d;

import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DetectiveToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragTheCanal extends CardImpl {

    public DragTheCanal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{B}");

        // Create a 2/2 white and blue Detective creature token. If a creature died this turn, you gain 2 life, surveil 2, then investigate.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DetectiveToken()));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(2), MorbidCondition.instance,
                "If a creature died this turn, you gain 2 life"
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SurveilEffect(2), MorbidCondition.instance, ", surveil 2"
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new InvestigateEffect(), MorbidCondition.instance, ", then investigate"
        ));
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private DragTheCanal(final DragTheCanal card) {
        super(card);
    }

    @Override
    public DragTheCanal copy() {
        return new DragTheCanal(this);
    }
}
