package mage.cards.t;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.RandomUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TwilightDiviner extends CardImpl {

    public TwilightDiviner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // Whenever one or more other creatures you control enter, if they entered or were cast from a graveyard, create a token that's a copy of one of them. This ability triggers only once each turn.
        this.addAbility(new TwilightDivinerTriggeredAbility());
    }

    private TwilightDiviner(final TwilightDiviner card) {
        super(card);
    }

    @Override
    public TwilightDiviner copy() {
        return new TwilightDiviner(this);
    }
}

class TwilightDivinerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    TwilightDivinerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TwilightDivinerEffect());
        setTriggerPhrase("Whenever one or more other creatures you control enter, if they entered or were cast from a graveyard, ");
        setTriggersLimitEachTurn(1);
    }

    private TwilightDivinerTriggeredAbility(final TwilightDivinerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TwilightDivinerTriggeredAbility copy() {
        return new TwilightDivinerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<Permanent> permanents = this
                .getFilteredEvents((ZoneChangeBatchEvent) event, game)
                .stream()
                .map(ZoneChangeEvent::getTarget)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(permanents, game));
        return true;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getToZone() != Zone.BATTLEFIELD) {
            return false;
        }
        Permanent permanent = event.getTarget();
        if (permanent == null || !StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        switch (event.getFromZone()) {
            case GRAVEYARD:
                return true;
            case STACK:
                return Optional
                        .ofNullable(permanent)
                        .map(MageItem::getId)
                        .map(game::getSpellOrLKIStack)
                        .map(Spell::getFromZone)
                        .filter(Zone.GRAVEYARD::match)
                        .isPresent();
            default:
                return false;
        }
    }
}

class TwilightDivinerEffect extends OneShotEffect {

    TwilightDivinerEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of one of them";
    }

    private TwilightDivinerEffect(final TwilightDivinerEffect effect) {
        super(effect);
    }

    @Override
    public TwilightDivinerEffect copy() {
        return new TwilightDivinerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                permanent = RandomUtil.randomFromCollection(permanents);
                break;
            default:
                Player player = game.getPlayer(source.getControllerId());
                if (player == null) {
                    return false;
                }
                FilterPermanent filter = new FilterPermanent("permanent to copy");
                filter.add(Predicates.or(permanents
                        .stream()
                        .map(MageItem::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toSet())));
                TargetPermanent target = new TargetPermanent(filter);
                player.choose(Outcome.Copy, target, source, game);
                permanent = game.getPermanent(target.getFirstTarget());
        }
        return permanent != null && new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
    }
}
