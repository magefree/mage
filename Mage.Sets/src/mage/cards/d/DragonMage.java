
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DragonMage extends CardImpl {

    public DragonMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Dragon Mage deals combat damage to a player, each player discards their hand and draws seven cards.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardHandAllEffect(), false);
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public DragonMage(final DragonMage card) {
        super(card);
    }

    @Override
    public DragonMage copy() {
        return new DragonMage(this);
    }
}