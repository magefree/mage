package mage.cards.a;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author muz
 */
public final class AvengersDisassembled extends CardImpl {

    public AvengersDisassembled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Avengers Disassembled deals 3 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));

        // * Destroy target land. Its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        Mode mode2 = new Mode(new DestroyTargetEffect());
        mode2.addEffect(new SearchLibraryPutInPlayTargetControllerEffect(true));
        mode2.addTarget(new TargetLandPermanent());
        this.getSpellAbility().addMode(mode2);
    }

    private AvengersDisassembled(final AvengersDisassembled card) {
        super(card);
    }

    @Override
    public AvengersDisassembled copy() {
        return new AvengersDisassembled(this);
    }
}
