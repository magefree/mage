package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DealtCombatDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WallOfEssence extends CardImpl {

    public WallOfEssence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Wall of Essence is dealt combat damage, you gain that much life.
        this.addAbility(new DealtCombatDamageToSourceTriggeredAbility(new GainLifeEffect(SavedDamageValue.MUCH), false));
    }

    private WallOfEssence(final WallOfEssence card) {
        super(card);
    }

    @Override
    public WallOfEssence copy() {
        return new WallOfEssence(this);
    }
}
