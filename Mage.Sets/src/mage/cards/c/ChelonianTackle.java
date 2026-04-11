package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ChelonianTackle extends CardImpl {

    public ChelonianTackle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Target creature you control gets +0/+10 until end of turn. Then it fights up to one target creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(0, 10));
        this.getSpellAbility().addEffect(new FightTargetsEffect()
            .setText("Then it fights up to one target creature an opponent controls"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 1));
    }

    private ChelonianTackle(final ChelonianTackle card) {
        super(card);
    }

    @Override
    public ChelonianTackle copy() {
        return new ChelonianTackle(this);
    }
}
