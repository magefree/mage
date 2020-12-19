package mage.cards.e;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author weirddan455
 */
public final class ElvenAmbush extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf you control");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public ElvenAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Create a 1/1 green Elf Warrior creature token for each Elf you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfToken(), new PermanentsOnBattlefieldCount(filter)));
    }

    private ElvenAmbush(final ElvenAmbush card) {
        super(card);
    }

    @Override
    public ElvenAmbush copy() {
        return new ElvenAmbush(this);
    }
}
