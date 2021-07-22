package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DivinersLockbox extends CardImpl {

    public DivinersLockbox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}: Choose a card name, then reveal the top card of your library. If that card has the chosen name, sacrifice Diviner's Lockbox and draw three cards. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new DivinersLockboxEffect(), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DivinersLockbox(final DivinersLockbox card) {
        super(card);
    }

    @Override
    public DivinersLockbox copy() {
        return new DivinersLockbox(this);
    }
}

class DivinersLockboxEffect extends OneShotEffect {

    private static final Effect sacEffect = new SacrificeSourceEffect();

    DivinersLockboxEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a card name, then reveal the top card of your library. " +
                "If that card has the chosen name, sacrifice {this} and draw three cards.";
    }

    private DivinersLockboxEffect(final DivinersLockboxEffect effect) {
        super(effect);
    }

    @Override
    public DivinersLockboxEffect copy() {
        return new DivinersLockboxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(player, game, source, false);
        Card card = player.getLibrary().getFromTop(game);
        player.revealCards(source, new CardsImpl(card), game);
        if (CardUtil.haveSameNames(card, cardName, game)) {
            sacEffect.apply(game, source);
            player.drawCards(3, source, game);
        }
        return true;
    }
}