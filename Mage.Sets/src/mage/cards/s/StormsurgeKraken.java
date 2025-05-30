
package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormsurgeKraken extends CardImpl {

    public StormsurgeKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Lieutenant - As long as you control your commander, Stormsurge Kraken gets +2/+2 and has "Whenever Stormsurge Kraken becomes blocked, you may draw two cards."
        this.addAbility(new LieutenantAbility(new GainAbilitySourceEffect(
                new BecomesBlockedSourceTriggeredAbility(
                        new DrawCardSourceControllerEffect(2), true
                ), Duration.WhileOnBattlefield
        ), "and has \"Whenever {this} becomes blocked, you may draw two cards.\""));
    }

    private StormsurgeKraken(final StormsurgeKraken card) {
        super(card);
    }

    @Override
    public StormsurgeKraken copy() {
        return new StormsurgeKraken(this);
    }
}
