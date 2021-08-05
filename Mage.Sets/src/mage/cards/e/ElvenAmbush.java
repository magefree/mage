package mage.cards.e;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ElvenAmbush extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf you control");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    private static final DynamicValue elfCount = new PermanentsOnBattlefieldCount(filter);

    public ElvenAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        this.getSpellAbility().addHint(new ValueHint("Elves you control", elfCount));

        // Create a 1/1 green Elf Warrior creature token for each Elf you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfWarriorToken(), elfCount));
    }

    private ElvenAmbush(final ElvenAmbush card) {
        super(card);
    }

    @Override
    public ElvenAmbush copy() {
        return new ElvenAmbush(this);
    }
}
