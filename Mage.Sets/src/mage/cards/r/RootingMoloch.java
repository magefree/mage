package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RootingMoloch extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with a cycling ability from your graveyard");

    static {
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    public RootingMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Rooting Moloch enters the battlefield, exile target card with a cycling ability from your graveyard. Until the end of your next turn, you may play that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RootingMolochEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RootingMoloch(final RootingMoloch card) {
        super(card);
    }

    @Override
    public RootingMoloch copy() {
        return new RootingMoloch(this);
    }
}

class RootingMolochEffect extends OneShotEffect {

    RootingMolochEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card with a cycling ability from your graveyard. " +
                "Until the end of your next turn, you may play that card.";
    }

    private RootingMolochEffect(final RootingMolochEffect effect) {
        super(effect);
    }

    @Override
    public RootingMolochEffect copy() {
        return new RootingMolochEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null) {
            return false;
        }
        controller.moveCards(card, Zone.EXILED, source, game);
        ContinuousEffect effect = new RootingMolochMayPlayEffect();
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        return true;
    }
}

class RootingMolochMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    RootingMolochMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private RootingMolochMayPlayEffect(final RootingMolochMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public RootingMolochMayPlayEffect copy() {
        return new RootingMolochMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (castOnTurn != game.getTurnNum() && game.getPhase().getStep().getType() == PhaseStep.END_TURN) {
            return game.isActivePlayer(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(sourceId);
    }
}
