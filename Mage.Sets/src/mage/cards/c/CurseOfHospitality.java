package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfHospitality extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures attacking enchanted player");

    static {
        filter.add(CurseOfHospitalityPredicate.instance);
    }

    public CurseOfHospitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Creatures attacking enchanted player have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever a creature deals combat damage to enchanted player, that player exiles the top card of their library. Until end of turn, that creature's controller may play that card and they may spend mana as though it were mana of any color to cast that spell.
        this.addAbility(new CurseOfHospitalityTriggeredAbility());
    }

    private CurseOfHospitality(final CurseOfHospitality card) {
        super(card);
    }

    @Override
    public CurseOfHospitality copy() {
        return new CurseOfHospitality(this);
    }
}

enum CurseOfHospitalityPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = game.getPermanent(input.getSourceId());
        UUID defenderId = game.getCombat().getDefenderId(input.getObject().getId());
        return permanent != null && defenderId != null && defenderId.equals(permanent.getAttachedTo());
    }
}

class CurseOfHospitalityTriggeredAbility extends TriggeredAbilityImpl {

    CurseOfHospitalityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfHospitalityEffect());
    }

    private CurseOfHospitalityTriggeredAbility(final CurseOfHospitalityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfHospitalityTriggeredAbility copy() {
        return new CurseOfHospitalityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent dEvent = (DamagedEvent) event;
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (!dEvent.isCombatDamage() || permanent == null
                || !dEvent.getPlayerId().equals(permanent.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(game.getControllerId(dEvent.getSourceId())));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to enchanted player, " +
                "that player exiles the top card of their library. " +
                "Until end of turn, that creature's controller may play that card " +
                "and they may spend mana as though it were mana of any color to cast that spell.";
    }
}

class CurseOfHospitalityEffect extends OneShotEffect {

    CurseOfHospitalityEffect() {
        super(Outcome.Benefit);
    }

    private CurseOfHospitalityEffect(final CurseOfHospitalityEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfHospitalityEffect copy() {
        return new CurseOfHospitalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        Player enchanted = game.getPlayer(permanent.getAttachedTo());
        if (enchanted == null) {
            return false;
        }
        Card card = enchanted.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        enchanted.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceLogName(game, source)
        );
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return true;
        }
        CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true, player.getId(), null);
        return true;
    }
}
