
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class Switcheroo extends CardImpl {

    private static final String rule = "Exchange control of two target creatures";

    public Switcheroo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");


        // Exchange control of two target creatures.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, rule));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private Switcheroo(final Switcheroo card) {
        super(card);
    }

    @Override
    public Switcheroo copy() {
        return new Switcheroo(this);
    }
}
