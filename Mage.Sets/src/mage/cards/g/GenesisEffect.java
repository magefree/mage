package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.NewPlanetToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author muz
 */
public final class GenesisEffect extends CardImpl {

    public GenesisEffect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Genesis Effect deals 6 damage to target creature. Destroy target land you control. Create a tapped Planet land token named New Planet with "{T}: Add one mana of any color."
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new NewPlanetToken(), 1, true));
    }

    private GenesisEffect(final GenesisEffect card) {
        super(card);
    }

    @Override
    public GenesisEffect copy() {
        return new GenesisEffect(this);
    }
}
