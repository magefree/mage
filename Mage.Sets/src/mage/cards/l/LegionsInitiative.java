package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class LegionsInitiative extends CardImpl {

    private static final FilterCreaturePermanent filterRedCreature
            = new FilterCreaturePermanent("Red creatures");
    private static final FilterCreaturePermanent filterWhiteCreature
            = new FilterCreaturePermanent("White creatures");

    static {
        filterRedCreature.add(new ColorPredicate(ObjectColor.RED));
        filterWhiteCreature.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public LegionsInitiative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        // Red creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filterRedCreature
        )));

        // White creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                0, 1, Duration.WhileOnBattlefield, filterWhiteCreature
        )));

        // {R}{W}, Exile Legion's Initiative: Exile all creatures you control. At the beginning of the next combat, return those cards to the battlefield under their owner's control and those creatures gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new LegionsInitiativeExileEffect(), new ManaCostsImpl<>("{R}{W}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfCombatDelayedTriggeredAbility(new LegionsInitiativeReturnFromExileEffect())
        ));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private LegionsInitiative(final LegionsInitiative card) {
        super(card);
    }

    @Override
    public LegionsInitiative copy() {
        return new LegionsInitiative(this);
    }
}

class LegionsInitiativeExileEffect extends OneShotEffect {

    LegionsInitiativeExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creatures you control.";
    }

    private LegionsInitiativeExileEffect(final LegionsInitiativeExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        ).stream().filter(Objects::nonNull).forEach(cards::add);
        return player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
    }

    @Override
    public LegionsInitiativeExileEffect copy() {
        return new LegionsInitiativeExileEffect(this);
    }
}

class LegionsInitiativeReturnFromExileEffect extends OneShotEffect {

    LegionsInitiativeReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "return those cards to the battlefield under their owner's control " +
                "and those creatures gain haste until end of turn";
    }

    private LegionsInitiativeReturnFromExileEffect(final LegionsInitiativeReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public LegionsInitiativeReturnFromExileEffect copy() {
        return new LegionsInitiativeReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -1));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone);
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        List<Permanent> permanents = cards.stream().map(game::getPermanent).filter(Objects::nonNull).collect(Collectors.toList());
        if (permanents.isEmpty()) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
