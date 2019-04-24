
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterSpellOrPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetSpellOrPermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author LevelX2
 */
public final class BrutalExpulsion extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or creature");
    static {
        filter.setPermanentFilter(new FilterCreaturePermanent());
    }

    public BrutalExpulsion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Choose one or both
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // - Return target spell or creature to its owner's hand;
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent(1, 1, filter, false));
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        // or Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        Mode mode = new Mode();
        mode.getTargets().add(new TargetCreatureOrPlaneswalker());
        mode.getEffects().add(new DamageTargetEffect(2));
        Effect effect = new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn);
        effect.setText("If that permanent would be put into a graveyard this turn, exile it instead");
        mode.getEffects().add(effect);
        this.getSpellAbility().addMode(mode);
        this.getSpellAbility().addWatcher(new DamagedByWatcher(true));
    }

    public BrutalExpulsion(final BrutalExpulsion card) {
        super(card);
    }

    @Override
    public BrutalExpulsion copy() {
        return new BrutalExpulsion(this);
    }
}
