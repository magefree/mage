
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 * @author Loki
 */
public final class NezumiGraverobber extends CardImpl {

    public NezumiGraverobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Nighteyes the Desecrator";

        // {1}{B}: Exile target card from an opponent's graveyard. If no cards are in that graveyard, flip Nezumi Graverobber.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        Target target = new TargetCardInOpponentsGraveyard(new FilterCard("card from an opponent's graveyard"));
        ability.addTarget(target);
        ability.addEffect(new NezumiGraverobberFlipEffect());
        this.addAbility(ability);
    }

    private NezumiGraverobber(final NezumiGraverobber card) {
        super(card);
    }

    @Override
    public NezumiGraverobber copy() {
        return new NezumiGraverobber(this);
    }
}

class NezumiGraverobberFlipEffect extends OneShotEffect {

    NezumiGraverobberFlipEffect() {
        super(Outcome.BecomeCreature);
        staticText = "If no cards are in that graveyard, flip {this}";
    }

    private NezumiGraverobberFlipEffect(final NezumiGraverobberFlipEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }

        Player player = game.getPlayer(card.getOwnerId());
        if (player == null) {
            return false;
        }

        if (player.getGraveyard().isEmpty()) {
            Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{4}{B}"));
            ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));

            CreatureToken flipToken = new CreatureToken(4, 2, "", SubType.RAT, SubType.WIZARD)
                .withName("Nighteyes the Desecrator")
                .withSuperType(SuperType.LEGENDARY)
                .withColor("B")
                .withAbility(ability);

            return new FlipSourceEffect(flipToken).apply(game, source);
        }
        return false;
    }

    @Override
    public NezumiGraverobberFlipEffect copy() {
        return new NezumiGraverobberFlipEffect(this);
    }

}
