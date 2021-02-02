
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.InsectToken;
import mage.players.Player;

/**
 *
 * @author ilcartographer
 */
public final class LivingHive extends CardImpl {

    public LivingHive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Living Hive deals combat damage to a player, create that many 1/1 green Insect creature tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LivingHiveEffect(), false, true));
    }

    private LivingHive(final LivingHive card) {
        super(card);
    }

    @Override
    public LivingHive copy() {
        return new LivingHive(this);
    }
}

class LivingHiveEffect extends OneShotEffect {
    
    public LivingHiveEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create that many 1/1 green Insect creature tokens";
    }
    
    public LivingHiveEffect(final LivingHiveEffect effect) {
        super(effect);
    }
    
    @Override
    public LivingHiveEffect copy() {
        return new LivingHiveEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer)getValue("damage");
            if (amount > 0) {
               return new CreateTokenEffect(new InsectToken(), amount).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
