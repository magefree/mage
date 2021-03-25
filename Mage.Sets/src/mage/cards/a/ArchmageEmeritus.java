package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchmageEmeritus extends CardImpl {

    public ArchmageEmeritus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, draw a card.
        this.addAbility(new MagecraftAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ArchmageEmeritus(final ArchmageEmeritus card) {
        super(card);
    }

    @Override
    public ArchmageEmeritus copy() {
        return new ArchmageEmeritus(this);
    }
}
