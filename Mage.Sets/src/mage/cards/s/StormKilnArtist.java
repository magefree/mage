package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormKilnArtist extends CardImpl {

    public StormKilnArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Storm-Kiln Artist gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                ArtifactYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+0 for each artifact you control")).addHint(ArtifactYouControlHint.instance));

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, create a Treasure token.
        this.addAbility(new MagecraftAbility(new CreateTokenEffect(new TreasureToken())));
    }

    private StormKilnArtist(final StormKilnArtist card) {
        super(card);
    }

    @Override
    public StormKilnArtist copy() {
        return new StormKilnArtist(this);
    }
}
