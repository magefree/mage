package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Asmoranomardicadaistinaculdacar extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card named The Underworld Cookbook");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.FOOD, "Foods");

    static {
        filter.add(new NamePredicate("The Underworld Cookbook"));
    }

    public Asmoranomardicadaistinaculdacar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.color.setBlack(true);

        // As long as you've discarded a card this turn, you may pay {B/R} to cast this spell.
        this.addAbility(new AlternativeCostSourceAbility(
                new ManaCostsImpl<>("{B/R}"), AsmoranomardicadaistinaculdacarCondition.instance,
                "as long as you've discarded a card this turn, you may pay {B/R} to cast this spell"
        ), new AsmoranomardicadaistinaculdacarWatcher());

        // When Asmoranomardicadaistinaculdacar enters the battlefield, you may search your library for a card named The Underworld Cookbook, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Sacrifice two Foods: Target creature deals 6 damage to itself.
        Ability ability = new SimpleActivatedAbility(
                new AsmoranomardicadaistinaculdacarEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(2, filter2))
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Asmoranomardicadaistinaculdacar(final Asmoranomardicadaistinaculdacar card) {
        super(card);
    }

    @Override
    public Asmoranomardicadaistinaculdacar copy() {
        return new Asmoranomardicadaistinaculdacar(this);
    }
}

enum AsmoranomardicadaistinaculdacarCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return AsmoranomardicadaistinaculdacarWatcher.checkPlayer(source.getControllerId(), game);
    }
}

class AsmoranomardicadaistinaculdacarEffect extends OneShotEffect {

    AsmoranomardicadaistinaculdacarEffect() {
        super(Outcome.Benefit);
        staticText = "target creature deals 6 damage to itself";
    }

    private AsmoranomardicadaistinaculdacarEffect(final AsmoranomardicadaistinaculdacarEffect effect) {
        super(effect);
    }

    @Override
    public AsmoranomardicadaistinaculdacarEffect copy() {
        return new AsmoranomardicadaistinaculdacarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return permanent.damage(6, permanent.getId(), source, game) > 0;
    }
}

class AsmoranomardicadaistinaculdacarWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    AsmoranomardicadaistinaculdacarWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DISCARDED_CARD) {
            playerSet.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        playerSet.clear();
        super.reset();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        AsmoranomardicadaistinaculdacarWatcher watcher
                = game.getState().getWatcher(AsmoranomardicadaistinaculdacarWatcher.class);
        return watcher != null && watcher.playerSet.contains(playerId);
    }
}
// it's easier to pronounce if you break it into separate words: asmorano mardica daistina culdacar
