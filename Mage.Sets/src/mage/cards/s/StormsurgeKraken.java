
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.LieutenantAbility;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class StormsurgeKraken extends CardImpl {

    public StormsurgeKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
        
        // Lieutenant - As long as you control your commander, Stormsurge Kraken gets +2/+2 and has "Whenever Stormsurge Kraken becomes blocked, you may draw two cards."
        ContinuousEffect effect = new GainAbilitySourceEffect(new BecomesBlockedSourceTriggeredAbility(new DrawCardSourceControllerEffect(2), true), Duration.WhileOnBattlefield);
        effect.setText("and has \"Whenever Stormsurge Kraken becomes blocked, you may draw two cards.\"");
        this.addAbility(new LieutenantAbility(effect));
    }

    private StormsurgeKraken(final StormsurgeKraken card) {
        super(card);
    }

    @Override
    public StormsurgeKraken copy() {
        return new StormsurgeKraken(this);
    }
}
