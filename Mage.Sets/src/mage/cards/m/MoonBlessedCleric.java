package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonBlessedCleric extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard();

    public MoonBlessedCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Divine Intervention — When Moon-Blessed Cleric enters the battlefield, you may search your library for an enchantment card, reveal it, then shuffle and put that card on top.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(
                        new TargetCardInLibrary(filter), true
                ), true
        ).withFlavorWord("Divine Intervention"));
    }

    private MoonBlessedCleric(final MoonBlessedCleric card) {
        super(card);
    }

    @Override
    public MoonBlessedCleric copy() {
        return new MoonBlessedCleric(this);
    }
}
