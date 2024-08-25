package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrickstersElk extends CardImpl {

    public TrickstersElk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {1}{G}
        this.addAbility(new BestowAbility(this, "{1}{G}"));

        // Enchanted creature loses all abilities and is a green Elk creature with base power and toughness 3/3.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(3, 3, "", SubType.ELK).withColor("G"),
                "Enchanted creature loses all abilities and is a green Elk creature with base power and toughness 3/3",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL
        )));
    }

    private TrickstersElk(final TrickstersElk card) {
        super(card);
    }

    @Override
    public TrickstersElk copy() {
        return new TrickstersElk(this);
    }
}
