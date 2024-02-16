
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class HopeCharm extends CardImpl {

   private static final FilterPermanent filter = new FilterPermanent("Aura");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public HopeCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Choose one - Target creature gains first strike until end of turn
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or target player gains 2 life
        Mode mode = new Mode(new GainLifeTargetEffect(2));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
        // or destroy target Aura.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private HopeCharm(final HopeCharm card) {
        super(card);
    }

    @Override
    public HopeCharm copy() {
        return new HopeCharm(this);
    }
}
