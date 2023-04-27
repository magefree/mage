
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class FathomFeeder extends CardImpl {

    public FathomFeeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ingest
        this.addAbility(new IngestAbility());

        // {3}{U}{B}: Draw a card. Each opponent exiles the top card of their library.
        Effect effect = new FathomFeederEffect();
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}{U}{B}"));
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FathomFeeder(final FathomFeeder card) {
        super(card);
    }

    @Override
    public FathomFeeder copy() {
        return new FathomFeeder(this);
    }
}

class FathomFeederEffect extends OneShotEffect {
    public FathomFeederEffect() {
        super(Outcome.Exile);
        this.staticText = "Each opponent exiles the top card of their library";
    }

    public FathomFeederEffect(final FathomFeederEffect effect) {
        super(effect);
    }

    @Override
    public FathomFeederEffect copy() {
        return new FathomFeederEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null) {
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    player.moveCards(card, Zone.EXILED, source, game);
                }
            }
        }
        return true;
    }
}
