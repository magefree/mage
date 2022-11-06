package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SettleBeyondReality extends CardImpl {

    public SettleBeyondReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Exile target creature you don't control.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));

        // • Exile target creature you control, then return it to the battlefield under its owner's control.
        Mode mode = new Mode(new ExileTargetForSourceEffect());
        mode.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false, "it").concatBy(", then"));
        mode.addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SettleBeyondReality(final SettleBeyondReality card) {
        super(card);
    }

    @Override
    public SettleBeyondReality copy() {
        return new SettleBeyondReality(this);
    }
}
