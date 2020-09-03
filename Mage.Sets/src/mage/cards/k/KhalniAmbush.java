package mage.cards.k;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KhalniAmbush extends CardImpl {

    public KhalniAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.k.KhalniTerritory.class;

        // Target creature you control fights target creature you don't control.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private KhalniAmbush(final KhalniAmbush card) {
        super(card);
    }

    @Override
    public KhalniAmbush copy() {
        return new KhalniAmbush(this);
    }
}
