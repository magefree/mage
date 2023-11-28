package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class SorcerousSquall extends CardImpl {

    public SorcerousSquall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}{U}{U}");

        // Delve
        this.addAbility(new DelveAbility());

        // Target opponent mills nine cards, then you may cast an instant or sorcery spell from that player's graveyard without paying its mana cost. If that spell would be put into a graveyard, exile it instead.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(9));
        this.getSpellAbility().addEffect(new SorcerousSquallEffect().concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private SorcerousSquall(final SorcerousSquall card) {
        super(card);
    }

    @Override
    public SorcerousSquall copy() {
        return new SorcerousSquall(this);
    }
}


class SorcerousSquallEffect extends OneShotEffect {

    SorcerousSquallEffect() {
        super(Outcome.Detriment);
        setText("you may cast an instant or sorcery spell from that player's graveyard without paying its mana cost. "+ ThatSpellGraveyardExileReplacementEffect.RULE_A);
    }

    private SorcerousSquallEffect(final SorcerousSquallEffect effect) {
        super(effect);
    }

    @Override
    public SorcerousSquallEffect copy() {
        return new SorcerousSquallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from that player's graveyard");
        filter.add(new OwnerIdPredicate(source.getFirstTarget()));
        Target target = new TargetCardInGraveyard(1, 1, filter, true);
        player.choose(outcome, target, source, game);
        Effect effect = new MayCastTargetThenExileEffect(true);
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
        effect.apply(game, source);
        return true;
    }
}
