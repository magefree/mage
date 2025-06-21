package mage.cards.p;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL;

/**
 * @author BetaSteward
 */
public final class PreyUpon extends CardImpl {

    public PreyUpon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private PreyUpon(final PreyUpon card) {
        super(card);
    }

    @Override
    public PreyUpon copy() {
        return new PreyUpon(this);
    }
}
