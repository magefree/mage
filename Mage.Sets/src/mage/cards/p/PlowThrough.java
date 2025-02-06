package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlowThrough extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VEHICLE, "Vehicle");

    public PlowThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose one --
        // * Target creature you control fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        // * Destroy target Vehicle.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private PlowThrough(final PlowThrough card) {
        super(card);
    }

    @Override
    public PlowThrough copy() {
        return new PlowThrough(this);
    }
}
