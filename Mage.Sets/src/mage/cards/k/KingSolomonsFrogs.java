package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DarioBarrabinoDev
 */
public final class KingSolomonsFrogs extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with mana value 3 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public KingSolomonsFrogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When King Solomon's Frogs enters, if you cast it, for each opponent, exile up to one target permanent that player controls with mana value 3 or greater. For each permanent exiled this way, its controller draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new KingSolomonsFrogsEffect().setTargetPointer(new EachTargetPointer())
        ).withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);

        // {3}, {T}, Exile King Solomon's Frogs: You become the monarch.
        ability = new SimpleActivatedAbility(new BecomesMonarchSourceEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        ability.addHint(MonarchHint.instance);
        this.addAbility(ability);
    }

    private KingSolomonsFrogs(final KingSolomonsFrogs card) {
        super(card);
    }

    @Override
    public KingSolomonsFrogs copy() {
        return new KingSolomonsFrogs(this);
    }
}

class KingSolomonsFrogsEffect extends OneShotEffect {

    KingSolomonsFrogsEffect() {
        super(Outcome.Removal);
        staticText = "for each opponent, exile up to one target permanent that player controls "
                + "with mana value 3 or greater. For each permanent exiled this way, its controller draws a card";
    }

    private KingSolomonsFrogsEffect(final KingSolomonsFrogsEffect effect) {
        super(effect);
    }

    @Override
    public KingSolomonsFrogsEffect copy() {
        return new KingSolomonsFrogsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Permanent> permanents = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }
        // controllers must be known before the permanents leave the battlefield
        Map<UUID, UUID> controllers = permanents
                .stream()
                .collect(Collectors.toMap(Permanent::getId, Permanent::getControllerId));
        player.moveCards(permanents, Zone.EXILED, source, game);
        Map<UUID, Integer> playerMap = new HashMap<>();
        for (Map.Entry<UUID, UUID> entry : controllers.entrySet()) {
            if (game.getState().getZone(entry.getKey()) == Zone.EXILED) {
                playerMap.merge(entry.getValue(), 1, Integer::sum);
            }
        }
        for (Map.Entry<UUID, Integer> entry : playerMap.entrySet()) {
            Player controller = game.getPlayer(entry.getKey());
            if (controller != null) {
                controller.drawCards(entry.getValue(), source, game);
            }
        }
        return true;
    }
}
