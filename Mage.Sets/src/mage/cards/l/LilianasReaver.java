
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author LevelX2
 */
public final class LilianasReaver extends CardImpl {

    public LilianasReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Whenever Liliana's Reaver deals combat damage to a player, that player discards a card and you create a tapped 2/2 black Zombie creature token.

        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true);
        ability.addEffect(new CreateTokenEffect(new ZombieToken(), 1, true, false).concatBy("and you"));
        this.addAbility(ability);
    }

    private LilianasReaver(final LilianasReaver card) {
        super(card);
    }

    @Override
    public LilianasReaver copy() {
        return new LilianasReaver(this);
    }
}
