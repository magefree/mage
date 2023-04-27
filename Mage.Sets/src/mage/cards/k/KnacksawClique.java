package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class KnacksawClique extends CardImpl {

    public KnacksawClique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}, {untap}: Target opponent exiles the top card of their library. Until end of turn, you may play that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnacksawCliqueEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    private KnacksawClique(final KnacksawClique card) {
        super(card);
    }

    @Override
    public KnacksawClique copy() {
        return new KnacksawClique(this);
    }
}

class KnacksawCliqueEffect extends OneShotEffect {

    public KnacksawCliqueEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent exiles the top card of their library. Until end of turn, you may play that card";
    }

    public KnacksawCliqueEffect(final KnacksawCliqueEffect effect) {
        super(effect);
    }

    @Override
    public KnacksawCliqueEffect copy() {
        return new KnacksawCliqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && opponent != null) {
            if (opponent.getLibrary().hasCards()) {
                Library library = opponent.getLibrary();
                Card card = library.getFromTop(game);
                if (card != null) {
                    opponent.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getName(), source, game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new KnacksawCliqueCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId(), game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class KnacksawCliqueCastFromExileEffect extends AsThoughEffectImpl {

    public KnacksawCliqueCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, you may play that card";
    }

    public KnacksawCliqueCastFromExileEffect(final KnacksawCliqueCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KnacksawCliqueCastFromExileEffect copy() {
        return new KnacksawCliqueCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && sourceId.equals(getTargetPointer().getFirst(game, source));

    }
}
