package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingAloneCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class YuanTiMalison extends CardImpl {

    public YuanTiMalison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Yuan-Ti Malison can't be blocked as long as it's attacking alone.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                SourceAttackingAloneCondition.instance,
                "{this} can't be blocked as long as it's attacking alone"
        )));

        // Whenever Yuan-Ti Malison deals combat damage to a player, venture into the dungeon.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new VentureIntoTheDungeonEffect(), false));
    }

    private YuanTiMalison(final YuanTiMalison card) {
        super(card);
    }

    @Override
    public YuanTiMalison copy() {
        return new YuanTiMalison(this);
    }
}
