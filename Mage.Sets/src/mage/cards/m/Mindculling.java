
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class Mindculling extends CardImpl {

    public Mindculling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{U}");


        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, "you"));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2).concatBy("and"));
    }

    private Mindculling(final Mindculling card) {
        super(card);
    }

    @Override
    public Mindculling copy() {
        return new Mindculling(this);
    }
}
