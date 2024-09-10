package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UginsMastery extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a colorless creature spell");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public UginsMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}");

        // Whenever you cast a colorless creature spell, manifest the top card of your library.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ManifestEffect(1), filter, false));

        // Whenever you attack with creatures with total power 6 or greater, you may turn a face-down creature you control face up.
        this.addAbility(new UginsMasteryTriggeredAbility());
    }

    private UginsMastery(final UginsMastery card) {
        super(card);
    }

    @Override
    public UginsMastery copy() {
        return new UginsMastery(this);
    }
}

class UginsMasteryTriggeredAbility extends TriggeredAbilityImpl {

    UginsMasteryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UginsMasteryEffect());
        this.setTriggerPhrase("Whenever you attack with creatures with total power 6 or greater, ");
    }

    private UginsMasteryTriggeredAbility(final UginsMasteryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UginsMasteryTriggeredAbility copy() {
        return new UginsMasteryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum() >= 6;
    }
}

class UginsMasteryEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a face-down creature you control");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    UginsMasteryEffect() {
        super(Outcome.Benefit);
        staticText = "you may turn a face-down creature you control face up";
    }

    private UginsMasteryEffect(final UginsMasteryEffect effect) {
        super(effect);
    }

    @Override
    public UginsMasteryEffect copy() {
        return new UginsMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.turnFaceUp(source, game, source.getControllerId());
    }
}
