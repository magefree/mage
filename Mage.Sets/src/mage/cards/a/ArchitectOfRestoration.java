package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchitectOfRestoration extends CardImpl {

    public ArchitectOfRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setWhite(true);
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Architect of Restoration attacks or blocks, create a 1/1 colorless Spirit creature token.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new SpiritToken()), false));
    }

    private ArchitectOfRestoration(final ArchitectOfRestoration card) {
        super(card);
    }

    @Override
    public ArchitectOfRestoration copy() {
        return new ArchitectOfRestoration(this);
    }
}
