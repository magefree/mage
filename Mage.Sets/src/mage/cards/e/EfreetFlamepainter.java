package mage.cards.e;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardEnteringGraveyardReplacementEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class EfreetFlamepainter extends CardImpl {

    public EfreetFlamepainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Efreet Flamepainter deals combat damage to a player, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new EfreetFlamepainterEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        this.addAbility(ability);
    }

    private EfreetFlamepainter(final EfreetFlamepainter card) {
        super(card);
    }

    @Override
    public EfreetFlamepainter copy() {
        return new EfreetFlamepainter(this);
    }
}

class EfreetFlamepainterEffect extends OneShotEffect {

    EfreetFlamepainterEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead";
    }

    EfreetFlamepainterEffect(EfreetFlamepainterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetId = source.getFirstTarget();
        if (targetId != null) {
            Card card = game.getCard(targetId);
            if (card != null && controller.chooseUse(outcome, "Cast " + card.getName() + " without paying its mana cost?", source, game)) {
                game.addEffect(new ExileCardEnteringGraveyardReplacementEffect(card.getId()), source);
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(card, game, true),
                    game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            }
        }
        return true;
    }

    @Override
    public EfreetFlamepainterEffect copy() {
        return new EfreetFlamepainterEffect(this);
    }
}
