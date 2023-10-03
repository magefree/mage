
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward
 */
public final class BitterheartWitch extends CardImpl {

    public BitterheartWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(DeathtouchAbility.getInstance());

        // When Bitterheart Witch dies, you may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle your library.
        Ability ability = new DiesSourceTriggeredAbility(new BitterheartWitchEffect(), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private BitterheartWitch(final BitterheartWitch card) {
        super(card);
    }

    @Override
    public BitterheartWitch copy() {
        return new BitterheartWitch(this);
    }
}

class BitterheartWitchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Curse card in your library");

    static {
        filter.add(SubType.CURSE.getPredicate());
    }

    public BitterheartWitchEffect() {
        super(Outcome.Detriment);
        staticText = "you may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle";
    }

    private BitterheartWitchEffect(final BitterheartWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            TargetCardInLibrary targetCard = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(targetCard, source, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("attachTo:" + card.getId(), targetPlayer.getId());
                    if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                        targetPlayer.addAttachment(card.getId(), source, game);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public BitterheartWitchEffect copy() {
        return new BitterheartWitchEffect(this);
    }

}
