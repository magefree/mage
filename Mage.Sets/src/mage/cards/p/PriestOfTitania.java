package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 * @author BetaSteward_at_googlemail.com, North
 */
public final class PriestOfTitania extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "Elf on the battlefield");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public PriestOfTitania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G} for each Elf on the battlefield.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), xValue).addHint(new ValueHint("Elves on the battlefield", xValue)));
    }

    private PriestOfTitania(final PriestOfTitania card) {
        super(card);
    }

    @Override
    public PriestOfTitania copy() {
        return new PriestOfTitania(this);
    }
}
