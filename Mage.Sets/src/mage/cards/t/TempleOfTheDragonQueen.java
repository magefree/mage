package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterBySubtypeCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class TempleOfTheDragonQueen extends CardImpl {

    public TempleOfTheDragonQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // As Temple of the Dragon Queen enters the battlefield, you may reveal a Dragon card from your hand.
        // Temple of the Dragon Queen enters the battlefield tapped unless you revealed a Dragon card this way or you control a Dragon.
        this.addAbility(new AsEntersBattlefieldAbility(new TempleOfTheDragonQueenEffect()));

        // As Temple of the Dragon Queen enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private TempleOfTheDragonQueen(final TempleOfTheDragonQueen card) {
        super(card);
    }

    @Override
    public TempleOfTheDragonQueen copy() {
        return new TempleOfTheDragonQueen(this);
    }
}

class TempleOfTheDragonQueenEffect extends OneShotEffect {

    private static final FilterBySubtypeCard filter = new FilterBySubtypeCard(SubType.DRAGON);

    public TempleOfTheDragonQueenEffect() {
        super(Outcome.Tap);
        this.staticText = "you may reveal a Dragon card from your hand. "
                + "{this} enters the battlefield tapped unless you revealed a Dragon card this way or you control a Dragon";
    }

    private TempleOfTheDragonQueenEffect(final TempleOfTheDragonQueenEffect effect) {
        super(effect);
    }

    @Override
    public TempleOfTheDragonQueenEffect copy() {
        return new TempleOfTheDragonQueenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = source.getSourcePermanentIfItStillExists(game);
        if (land == null) {
            land = game.getPermanentEntering(source.getSourceId());
        }
        if (land != null) {
            boolean entersTapped = true;
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                boolean dragonInHand = false;
                for (UUID cardId : controller.getHand()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.hasSubtype(SubType.DRAGON, game)) {
                        dragonInHand = true;
                        break;
                    }
                }
                if (dragonInHand && controller.chooseUse(Outcome.Untap, "Reveal a Dragon card from your hand?", source, game)) {
                    TargetCardInHand target = new TargetCardInHand(filter);
                    if (controller.chooseTarget(Outcome.Untap, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null && card.hasSubtype(SubType.DRAGON, game)) {
                            controller.revealCards(source, new CardsImpl(card), game);
                            entersTapped = false;
                        }
                    }
                }
                if (entersTapped) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
                        if (permanent != null && permanent.hasSubtype(SubType.DRAGON, game)) {
                            entersTapped = false;
                            break;
                        }
                    }
                }
            }
            if (entersTapped) {
                land.setTapped(true);
                return true;
            }
        }
        return false;
    }
}
