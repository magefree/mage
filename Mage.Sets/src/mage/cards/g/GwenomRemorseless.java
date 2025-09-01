package mage.cards.g;

import mage.MageIdentifier;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class GwenomRemorseless extends CardImpl {

    public GwenomRemorseless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Gwenom attacks, until end of turn you may look at the top card of your library any time and you may play cards from the top of your library. If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        ContinuousEffect libraryAnyTimeEffect = new LookAtTopCardOfLibraryAnyTimeEffect(Duration.EndOfTurn);
        libraryAnyTimeEffect.setText("until end of turn you may look at the top card of your library any time");
        libraryAnyTimeEffect.concatBy(" ");
        AsThoughEffectImpl playCardEffect = new GwenomRemorselessPlayTopCardEffect();
        playCardEffect.concatBy("and");
        this.addAbility(new AttacksTriggeredAbility(new AddContinuousEffectToGame(libraryAnyTimeEffect, playCardEffect))
                .setIdentifier(MageIdentifier.GwenomRemorselessAlternateCast));
    }

    private GwenomRemorseless(final GwenomRemorseless card) {
        super(card);
    }

    @Override
    public GwenomRemorseless copy() {
        return new GwenomRemorseless(this);
    }
}

class GwenomRemorselessPlayTopCardEffect extends AsThoughEffectImpl {

    GwenomRemorselessPlayTopCardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.EndOfTurn, Outcome.AIDontUseIt); // AI will need help with this
        staticText = "you may play cards from the top of your library. If you cast a spell this way, "
                + "pay life equal to its mana value rather than pay its mana cost.";
    }

    private GwenomRemorselessPlayTopCardEffect(final GwenomRemorselessPlayTopCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GwenomRemorselessPlayTopCardEffect copy() {
        return new GwenomRemorselessPlayTopCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // current card's part
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be your card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null || !player.getId().equals(affectedControllerId)) {
            return false;
        }

        // must be from your library
        Card topCard = player.getLibrary().getFromTop(game);
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }
        // allows to play/cast with alternative life cost
        if (!cardToCheck.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(cardToCheck.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
            player.setCastSourceIdWithAlternateMana(cardToCheck.getId(), null, newCosts, MageIdentifier.GwenomRemorselessAlternateCast);
        }
        return true;
    }
}
