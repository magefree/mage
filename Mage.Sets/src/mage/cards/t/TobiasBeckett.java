package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author NinthWorld
 */
public final class TobiasBeckett extends CardImpl {

    public TobiasBeckett(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Tobias Becket enters the battlefield, put a bounty counter on target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Bounty - Whenever a creature an opponent controls with a bounty counter on it dies, exile the top card of that player's library. You may cast cards exiled this way and spend mana as though it were mana of any type to cast that spell.
        this.addAbility(new BountyAbility(new TobiasBeckettEffect(), false, true));
    }

    private TobiasBeckett(final TobiasBeckett card) {
        super(card);
    }

    @Override
    public TobiasBeckett copy() {
        return new TobiasBeckett(this);
    }
}

// Based on GrenzoHavocRaiserEffect
class TobiasBeckettEffect extends OneShotEffect {

    public TobiasBeckettEffect() {
        super(Outcome.Exile);
        staticText = "exile the top card of that player's library. You may cast cards exiled this way and spend mana as though it were mana of any type to cast that spell";
    }

    public TobiasBeckettEffect(final TobiasBeckettEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent bountyTriggered = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (bountyTriggered != null) {
                Player opponent = game.getPlayer(bountyTriggered.getControllerId());
                if (opponent != null) {
                    MageObject sourceObject = game.getObject(source);
                    UUID exileId = CardUtil.getCardExileZoneId(game, source);
                    Card card = opponent.getLibrary().getFromTop(game);
                    if (card != null && sourceObject != null) {
                        // move card to exile
                        controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source, game, Zone.LIBRARY, true);
                        // Add effects only if the card has a spellAbility (e.g. not for lands).
                        if (card.getSpellAbility() != null) {
                            // allow to cast the card
                            // and you may spend mana as though it were mana of any color to cast it
                            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TobiasBeckettEffect copy() {
        return new TobiasBeckettEffect(this);
    }
}