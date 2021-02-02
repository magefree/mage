package mage.cards.d;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.EndTurnEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DaysUndoing extends CardImpl {

    public DaysUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player shuffles their hand and graveyard into their library, then draws seven cards. If it's your turn, end the turn.
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new EndTurnEffect(), MyTurnCondition.instance, "If it's your turn, end the turn"));
        this.getSpellAbility().addHint(MyTurnHint.instance);
    }

    private DaysUndoing(final DaysUndoing card) {
        super(card);
    }

    @Override
    public DaysUndoing copy() {
        return new DaysUndoing(this);
    }
}
