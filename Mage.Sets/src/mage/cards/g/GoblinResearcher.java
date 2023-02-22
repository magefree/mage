package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.AttackedThisTurnWatcher;
import java.util.Objects;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author @stwalsh4118
 */
public final class GoblinResearcher extends CardImpl {

    public GoblinResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Goblin Researcher enters the battlefield, exile the top card of your library. During any turn you attacked with Goblin Researcher, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinResearcherEffect().setText("exile the top card of your library. During any turn you attacked with Goblin Researcher, you may play that card."), false));
    }

    private GoblinResearcher(final GoblinResearcher card) {
        super(card);
    }

    @Override
    public GoblinResearcher copy() {
        return new GoblinResearcher(this);
    }
}

class GoblinResearcherAttackedThisTurnCondition implements Condition {
 
    private Ability ability;
    private UUID sourceId;
    
    GoblinResearcherAttackedThisTurnCondition(Ability source, UUID sourceId) {
        this.ability = source;
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ability == null) {
            ability = source;
        }
        if (!ability.isControlledBy(game.getActivePlayerId())) {
            return false;
        }

        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        return watcher != null && watcher.getAttackedThisTurnCreaturesPermanentLKI()
                .stream()
                .map(permanent -> permanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.getId().equals(sourceId));
    }

    @Override
    public String toString() {
        return "During that turn you attacked with a Goblin Researcher";
    }
}

class GoblinResearcherEffect extends OneShotEffect {

    GoblinResearcherEffect() {
        super(Outcome.Benefit);
    }

    private GoblinResearcherEffect(final GoblinResearcherEffect effect) {
        super(effect);
    }

    @Override
    public GoblinResearcherEffect copy() {
        return new GoblinResearcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));

        Ability copiedAbility = source.copy();
        copiedAbility.newId();
        copiedAbility.setSourceId(card.getId());
        copiedAbility.setControllerId(source.getControllerId());
        PlayFromNotOwnHandZoneTargetEffect playFromExile = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfGame);
        ConditionalAsThoughEffect castOnlyIfGoblinResearcherAttackedThisTurn = new ConditionalAsThoughEffect(playFromExile, new GoblinResearcherAttackedThisTurnCondition(copiedAbility, source.getSourceId()));
        playFromExile.setTargetPointer(new FixedTarget(card, game));
        castOnlyIfGoblinResearcherAttackedThisTurn.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(castOnlyIfGoblinResearcherAttackedThisTurn, copiedAbility);
        return true;
    }
}
