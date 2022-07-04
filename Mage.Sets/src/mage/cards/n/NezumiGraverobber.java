
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
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
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

    NezumiGraverobberFlipEffect(final NezumiGraverobberFlipEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                if (player.getGraveyard().isEmpty()) {
                    return new FlipSourceEffect(new NighteyesTheDesecratorToken()).apply(game, source);
                }
            }
        }
        return false;
    }

    @Override
    public NezumiGraverobberFlipEffect copy() {
        return new NezumiGraverobberFlipEffect(this);
    }

}

class NighteyesTheDesecratorToken extends TokenImpl {
    
    NighteyesTheDesecratorToken() {            
        super("Nighteyes the Desecrator", "");
       addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(2);
        // {4}{B}: Put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{4}{B}"));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }
    public NighteyesTheDesecratorToken(final NighteyesTheDesecratorToken token) {
        super(token);
    }

    public NighteyesTheDesecratorToken copy() {
        return new NighteyesTheDesecratorToken(this);
    }
}
