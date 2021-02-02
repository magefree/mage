
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class TanaTheBloodsower extends CardImpl {

    public TanaTheBloodsower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Tana, the Bloodsower deals combat damage to a player, create that many 1/1 green Saproling creature tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new TanaTheBloodsowerEffect(), false, true));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private TanaTheBloodsower(final TanaTheBloodsower card) {
        super(card);
    }

    @Override
    public TanaTheBloodsower copy() {
        return new TanaTheBloodsower(this);
    }
}

class TanaTheBloodsowerEffect extends OneShotEffect {
    
    public TanaTheBloodsowerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create that many 1/1 green Saproling creature tokens";
    }
    
    public TanaTheBloodsowerEffect(final TanaTheBloodsowerEffect effect) {
        super(effect);
    }
    
    @Override
    public TanaTheBloodsowerEffect copy() {
        return new TanaTheBloodsowerEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = (Integer)getValue("damage");
            if (amount > 0) {
               return new CreateTokenEffect(new SaprolingToken(), amount).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
