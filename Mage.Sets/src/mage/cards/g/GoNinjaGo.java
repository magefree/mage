package mage.cards.g;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class GoNinjaGo extends CardImpl {

    public GoNinjaGo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{W}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Exile target creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE));
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false));

        // * Go Ninja Go deals damage equal to the greatest power among creatures you control to target creature an opponent controls.
        Mode mode2 = new Mode(new DamageTargetEffect(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES));
        mode2.addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addMode(mode2);
    }

    private GoNinjaGo(final GoNinjaGo card) {
        super(card);
    }

    @Override
    public GoNinjaGo copy() {
        return new GoNinjaGo(this);
    }
}
