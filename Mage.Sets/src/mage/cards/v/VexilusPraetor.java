package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VexilusPraetor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("commanders you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    public VexilusPraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.CUSTODES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Aegis of the Emperor -- Commanders you control have protection from everything.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ProtectionFromEverythingAbility(), Duration.WhileOnBattlefield, filter
        )).withFlavorWord("Aegis of the Emperor"));
    }

    private VexilusPraetor(final VexilusPraetor card) {
        super(card);
    }

    @Override
    public VexilusPraetor copy() {
        return new VexilusPraetor(this);
    }
}
