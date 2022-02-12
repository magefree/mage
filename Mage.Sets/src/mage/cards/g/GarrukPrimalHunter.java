
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.WurmToken;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class GarrukPrimalHunter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public GarrukPrimalHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{G}{G}{G}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);

        this.setStartingLoyalty(3);

        // +1: Create a 3/3 green Beast creature token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new BeastToken()), 1));

        // -3: Draw cards equal to the greatest power among creatures you control.
        this.addAbility(new LoyaltyAbility(new GarrukPrimalHunterEffect(), -3));

        // -6: Create a 6/6 green Wurm creature token for each land you control.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new WurmToken(), new PermanentsOnBattlefieldCount(filter)), -6));
    }

    private GarrukPrimalHunter(final GarrukPrimalHunter card) {
        super(card);
    }

    @Override
    public GarrukPrimalHunter copy() {
        return new GarrukPrimalHunter(this);
    }

}

class GarrukPrimalHunterEffect extends OneShotEffect {

    GarrukPrimalHunterEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw cards equal to the greatest power among creatures you control";
    }

    GarrukPrimalHunterEffect(final GarrukPrimalHunterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = 0;
            for (Permanent p : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), game)) {
                if (p.getPower().getValue() > amount) {
                    amount = p.getPower().getValue();
                }
            }
            player.drawCards(amount, source, game);
            return true;
        }
        return false;
    }

    @Override
    public GarrukPrimalHunterEffect copy() {
        return new GarrukPrimalHunterEffect(this);
    }

}
