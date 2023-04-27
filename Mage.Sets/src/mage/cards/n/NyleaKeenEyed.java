package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyleaKeenEyed extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");

    public NyleaKeenEyed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to green is less than five, Nylea isn't a creature.
        this.addAbility(new SimpleStaticAbility(new LoseCreatureTypeSourceEffect(DevotionCount.G, 5))
                .addHint(DevotionCount.G.getHint()));

        // Creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // {2}{G}: Reveal the top card of your library. If it's a creature card, put it into your hand. Otherwise, you may put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(new NyleaKeenEyedEffect(), new ManaCostsImpl<>("{2}{G}")));
    }

    private NyleaKeenEyed(final NyleaKeenEyed card) {
        super(card);
    }

    @Override
    public NyleaKeenEyed copy() {
        return new NyleaKeenEyed(this);
    }
}

class NyleaKeenEyedEffect extends OneShotEffect {

    NyleaKeenEyedEffect() {
        super(Outcome.Detriment);
        staticText = "reveal the top card of your library. If it's a creature card, " +
                "put it into your hand. Otherwise, you may put it into your graveyard";
    }

    private NyleaKeenEyedEffect(final NyleaKeenEyedEffect effect) {
        super(effect);
    }

    @Override
    public NyleaKeenEyedEffect copy() {
        return new NyleaKeenEyedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isCreature(game)) {
            return player.moveCards(card, Zone.HAND, source, game);
        }
        if (!player.chooseUse(Outcome.Detriment, "Put " + card.getName() + " into your graveyard?", source, game)) {
            return true;
        }
        return player.moveCards(card, Zone.GRAVEYARD, source, game);
    }
}
