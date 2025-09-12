package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimateGreenGoblin extends CardImpl {

    public UltimateGreenGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, discard a card, then create a Treasure token.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy(", then"));
        this.addAbility(ability);

        // Mayhem {2}{B/R}
        this.addAbility(new MayhemAbility(this, "{2}{B/R}"));
    }

    private UltimateGreenGoblin(final UltimateGreenGoblin card) {
        super(card);
    }

    @Override
    public UltimateGreenGoblin copy() {
        return new UltimateGreenGoblin(this);
    }
}
