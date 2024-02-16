
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class ConsumingSinkhole extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creature");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public ConsumingSinkhole(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Choose one &mdash; Exile target land creature.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile target land creature");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Consuming Sinkhole deals 4 damage to target player.
        Mode mode = new Mode(new DamageTargetEffect(4));
        mode.addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addMode(mode);
    }

    private ConsumingSinkhole(final ConsumingSinkhole card) {
        super(card);
    }

    @Override
    public ConsumingSinkhole copy() {
        return new ConsumingSinkhole(this);
    }
}
