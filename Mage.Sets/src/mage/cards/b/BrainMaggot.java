package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class BrainMaggot extends CardImpl {

    public BrainMaggot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Brain Maggot enters the battlefield, target opponent reveals their hand and you choose a nonland card from it. Exile that card until Brain Maggot leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BrainMaggotExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BrainMaggot(final BrainMaggot card) {
        super(card);
    }

    @Override
    public BrainMaggot copy() {
        return new BrainMaggot(this);
    }
}

class BrainMaggotExileEffect extends OneShotEffect {

    BrainMaggotExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent reveals their hand and you choose a nonland card from it. Exile that card until {this} leaves the battlefield";
    }

    private BrainMaggotExileEffect(final BrainMaggotExileEffect effect) {
        super(effect);
    }

    @Override
    public BrainMaggotExileEffect copy() {
        return new BrainMaggotExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && opponent != null && sourcePermanent != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards(sourcePermanent.getIdName(), opponent.getHand(), game);

                FilterCard filter = new FilterNonlandCard("nonland card to exile");
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (opponent.getHand().count(filter, game) > 0 && controller.choose(Outcome.Exile, opponent.getHand(), target, source, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card == null) {
                        return true;
                    }
                    // If source permanent leaves the battlefield before its triggered ability resolves, the target card won't be exiled.
                    Effect effect = new ExileUntilSourceLeavesEffect(Zone.HAND);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;

    }
}
