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
 * @author Loki
 */
public final class ElvishPromenade extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf you control");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    private static final DynamicValue elfCount = new PermanentsOnBattlefieldCount(filter);

    public ElvishPromenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.getSpellAbility().addHint(new ValueHint("Elves you control", elfCount));

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfWarriorToken(), elfCount));
    }

    private ElvishPromenade(final ElvishPromenade card) {
        super(card);
    }

    @Override
    public ElvishPromenade copy() {
        return new ElvishPromenade(this);
    }
}
