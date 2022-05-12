package mage.cards.k;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.command.emblems.KayaTheInexorableEmblem;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class KayaTheInexorable extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");
    static {
        filter.add(TokenPredicate.FALSE);
    }

    public KayaTheInexorable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(5);

        // +1: Put a ghostform counter on up to one target nontoken creature. It gains "When this creature dies or is put into exile, return it to its owner's hand and create a 1/1 white Spirit creature token with flying."
        LoyaltyAbility ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.GHOSTFORM.createInstance()), 1);
        ability.addEffect(new GainAbilityTargetEffect(new KayaTheInexorableTriggeredAbility(), Duration.WhileOnBattlefield,
                "It gains \"When this creature dies or is put into exile, return it to its owner's hand and create a 1/1 white Spirit creature token with flying.\""));
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // −3: Exile target nonland permanent.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // −7: You get an emblem with "At the beginning of your upkeep, you may cast a legendary spell from your hand, from your graveyard, or from among cards you own in exile without paying its mana cost."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KayaTheInexorableEmblem()), -7));
    }

    private KayaTheInexorable(final KayaTheInexorable card) {
        super(card);
    }

    @Override
    public KayaTheInexorable copy() {
        return new KayaTheInexorable(this);
    }
}

class KayaTheInexorableTriggeredAbility extends TriggeredAbilityImpl {

    public KayaTheInexorableTriggeredAbility() {
        super(Zone.ALL, null, false);
    }

    private KayaTheInexorableTriggeredAbility(KayaTheInexorableTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return zEvent.getFromZone() == Zone.BATTLEFIELD
                    && (zEvent.getToZone() == Zone.GRAVEYARD
                    || zEvent.getToZone() == Zone.EXILED);
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTargetId().equals(this.getSourceId())) {
            this.getEffects().clear();
            this.addEffect(new KayaTheInexorableEffect(new MageObjectReference(zEvent.getTarget(), game)));
            this.addEffect(new CreateTokenEffect(new SpiritWhiteToken()));
            return true;
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        Permanent sourcePermanent = null;
        if (game.getState().getZone(getSourceId()) == Zone.BATTLEFIELD) {
            sourcePermanent = game.getPermanent(getSourceId());
        } else {
            if (game.getShortLivingLKI(getSourceId(), Zone.BATTLEFIELD)) {
                sourcePermanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD);
            }
        }
        if (sourcePermanent == null) {
            return false;
        }
        return hasSourceObjectAbility(game, sourcePermanent, event);
    }

    @Override
    public KayaTheInexorableTriggeredAbility copy() {
        return new KayaTheInexorableTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} dies or is put into exile, return it to its owner's hand and create a 1/1 white Spirit creature token with flying.";
    }
}

class KayaTheInexorableEffect extends OneShotEffect {

    private final MageObjectReference mor;

    KayaTheInexorableEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private KayaTheInexorableEffect(KayaTheInexorableEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public KayaTheInexorableEffect copy() {
        return new KayaTheInexorableEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(mor.getSourceId());
        if (card == null || card.getZoneChangeCounter(game) - 1 != mor.getZoneChangeCounter()) {
            return false;
        }
        return player.moveCards(card, Zone.HAND, source, game);
    }
}
