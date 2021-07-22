package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YuanTiFangBlade extends CardImpl {

    public YuanTiFangBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Yuan-Ti Fang-Blade deals combat damage to a player, venture into the dungeon.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new VentureIntoTheDungeonEffect(), false
        ));
    }

    private YuanTiFangBlade(final YuanTiFangBlade card) {
        super(card);
    }

    @Override
    public YuanTiFangBlade copy() {
        return new YuanTiFangBlade(this);
    }
}
