package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Ketsuban
 */
public final class SoldeviSentry extends CardImpl {

    public SoldeviSentry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // 1: Choose target opponent. Regenerate Soldevi Sentry. When it regenerates
        // this way, that player may draw a card.
        Ability ability = new SimpleActivatedAbility(new SoldeviSentryEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SoldeviSentry(final SoldeviSentry card) {
        super(card);
    }

    @Override
    public SoldeviSentry copy() {
        return new SoldeviSentry(this);
    }
}

class SoldeviSentryEffect extends RegenerateSourceEffect {

    public SoldeviSentryEffect() {
        super();
        this.staticText = "Choose target opponent. Regenerate {this}. When it regenerates this way, that player may draw a card";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //20110204 - 701.11
        Player opponent = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && permanent.regenerate(source, game)) {
            if (opponent != null) {
                if (opponent.chooseUse(Outcome.DrawCard, "Draw a card?", source, game)) {
                    opponent.drawCards(1, source, game);
                }
            }
            this.used = true;
            return true;
        }
        return false;
    }

}
