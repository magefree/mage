package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterOwnedCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LoneFox
 */
public final class CadaverousBloom extends CardImpl {

    public CadaverousBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{G}");

        // Exile a card from your hand: Add {B}{B} or {G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, 
                new CadaverousBloomManaEffect(Mana.BlackMana(2)), 
                new ExileFromHandCost(new TargetCardInHand(new FilterOwnedCard("a card from your hand")))));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, 
                new CadaverousBloomManaEffect(Mana.GreenMana(2)),                  
                new ExileFromHandCost(new TargetCardInHand(new FilterOwnedCard("a card from your hand")))));
    }

    public CadaverousBloom(final CadaverousBloom card) {
        super(card);
    }

    @Override
    public CadaverousBloom copy() {
        return new CadaverousBloom(this);
    }
}

class CadaverousBloomManaEffect extends BasicManaEffect {

    public CadaverousBloomManaEffect(Mana mana) {
        super(mana);
    }

    public CadaverousBloomManaEffect(final CadaverousBloomManaEffect effect) {
        super(effect);
    }

    @Override
    public CadaverousBloomManaEffect copy() {
        return new CadaverousBloomManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            List<Mana> netMana = new ArrayList<>();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                int count = player.getHand().size();          
                if (count > 0) {
                    Mana mana = new Mana(
                            getManaTemplate().getRed() * count,
                            getManaTemplate().getGreen() * count,
                            getManaTemplate().getBlue() * count,
                            getManaTemplate().getWhite() * count,
                            getManaTemplate().getBlack() * count,
                            getManaTemplate().getGeneric() * count,
                            getManaTemplate().getAny() * count,
                            getManaTemplate().getColorless() * count
                    );
                        
                    if (count > 0) {
                        netMana.add(mana);
                    }
                }
            }
            return netMana;
        }
        return super.getNetMana(game, source);
    }
}
