package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScreamingSwarm extends CardImpl {

    public ScreamingSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with one or more creatures, target player mills that many cards.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new MillCardsTargetEffect(ScreamingSwarmValue.instance)
                        .setText("target player mills that many cards"),
                0
        ).setTriggerPhrase("Whenever you attack with one or more creatures, ");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // {2}{U}: Put Screaming Swarm from your graveyard into your library second from the top.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ScreamingSwarmEffect(), new ManaCostsImpl<>("{2}{U}")));
    }

    private ScreamingSwarm(final ScreamingSwarm card) {
        super(card);
    }

    @Override
    public ScreamingSwarm copy() {
        return new ScreamingSwarm(this);
    }
}

enum ScreamingSwarmValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("attackers");
    }

    @Override
    public ScreamingSwarmValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

class ScreamingSwarmEffect extends OneShotEffect {

    ScreamingSwarmEffect() {
        super(Outcome.Benefit);
        staticText = "put {this} from your graveyard into your library second from the top";
    }

    private ScreamingSwarmEffect(final ScreamingSwarmEffect effect) {
        super(effect);
    }

    @Override
    public ScreamingSwarmEffect copy() {
        return new ScreamingSwarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        return player != null
                && sourceObject instanceof Card
                && player.putCardOnTopXOfLibrary(
                (Card) sourceObject, game, source, 2, true
        );
    }
}
