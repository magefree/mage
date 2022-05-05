
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class BuccaneersBravado extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public BuccaneersBravado(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one - 
        // Target creature gets +1/+1 and gains first strike until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn).setText("Target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and gains first strike until end of turn"));
        // Target Pirate gets +1/+1 and gains double strike until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(1, 1, Duration.EndOfTurn).setText("Target Pirate gets +1/+1"));
        mode.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn).setText("and gains double strike until end of turn"));
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private BuccaneersBravado(final BuccaneersBravado card) {
        super(card);
    }

    @Override
    public BuccaneersBravado copy() {
        return new BuccaneersBravado(this);
    }
}
