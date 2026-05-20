package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinGlasswright extends PrepareCard {

    public GoblinGlasswright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}", "Craft with Pride", CardType.SORCERY, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Craft with Pride
        // Sorcery {R}
        // Create a Treasure token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private GoblinGlasswright(final GoblinGlasswright card) {
        super(card);
    }

    @Override
    public GoblinGlasswright copy() {
        return new GoblinGlasswright(this);
    }
}
