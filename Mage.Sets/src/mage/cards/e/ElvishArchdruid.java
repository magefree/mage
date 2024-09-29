package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com, North
 */
public final class ElvishArchdruid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELF, "Elf creatures");
    private static final FilterControlledPermanent filterCount = new FilterControlledPermanent(SubType.ELF, "Elf you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filterCount);

    public ElvishArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {T}: Add {G} for each Elf you control.
        this.addAbility(new DynamicManaAbility(Mana.GreenMana(1), xValue).addHint(new ValueHint("Elves you control", xValue)));
    }

    private ElvishArchdruid(final ElvishArchdruid card) {
        super(card);
    }

    @Override
    public ElvishArchdruid copy() {
        return new ElvishArchdruid(this);
    }
}
