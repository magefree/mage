package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
/**
 *
 * @author EikePeace
 */
public final class BackInTheSaddle extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public BackInTheSaddle(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.getSpellAbility().addEffect(new BackInTheSaddleEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

    }

    public BackInTheSaddle(final BackInTheSaddle card) {
        super(card);
    }

    @Override
    public BackInTheSaddle copy() {
        return new BackInTheSaddle(this);
    }
}

class BackInTheSaddleEffect extends OneShotEffect {

    public BackInTheSaddleEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. It gains first strike until end of turn.";
    }

    public BackInTheSaddleEffect(final mage.cards.b.BackInTheSaddleEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.b.BackInTheSaddleEffect copy() {
        return new mage.cards.b.BackInTheSaddleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent creature = game.getPermanent(card.getId());
                if (creature != null) {
                    // gains haste
                    ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
