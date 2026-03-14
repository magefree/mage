package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AddEachControlledColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaeburrowElder extends CardImpl {

    public FaeburrowElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Faeburrow Elder gets +1/+1 for each color among permanents you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS,
                ColorsAmongControlledPermanentsCount.ALL_PERMANENTS,
                Duration.WhileOnBattlefield
        )));

        // {T}: For each color among permanents you control, add one mana of that color.
        this.addAbility(new AddEachControlledColorManaAbility());
    }

    private FaeburrowElder(final FaeburrowElder card) {
        super(card);
    }

    @Override
    public FaeburrowElder copy() {
        return new FaeburrowElder(this);
    }
}
