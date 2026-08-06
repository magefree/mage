package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.BaneslayerAngelToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class BalefangTheUnslayable extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Angels and from Archons");

    static {
        filter.add(Predicates.or(
            SubType.ANGEL.getPredicate(),
            SubType.ARCHON.getPredicate()
        ));
    }

    public BalefangTheUnslayable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from Angels and from Archons
        this.addAbility(new ProtectionAbility(filter));

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect()));

        // When Balefang enters, target opponent creates a tapped Baneslayer Angel token. The token is goaded for the rest of the game.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BalefangTheUnslayableEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BalefangTheUnslayable(final BalefangTheUnslayable card) {
        super(card);
    }

    @Override
    public BalefangTheUnslayable copy() {
        return new BalefangTheUnslayable(this);
    }
}

class BalefangTheUnslayableEffect extends OneShotEffect {

    BalefangTheUnslayableEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "target opponent creates a tapped Baneslayer Angel token. The token is goaded for the rest of the game.";
    }

    private BalefangTheUnslayableEffect(final BalefangTheUnslayableEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Token token = new BaneslayerAngelToken();
        token.putOntoBattlefield(1, game, source, player.getId());
        token.getLastAddedTokenIds().forEach(id -> game.addEffect(
            new GoadTargetEffect().setDuration(Duration.EndOfGame).setTargetPointer(new FixedTarget(id, game)), source
        ));
        return true;
    }

    @Override
    public BalefangTheUnslayableEffect copy() {
        return new BalefangTheUnslayableEffect(this);
    }
}
