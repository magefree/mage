package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class SunkenPalace extends CardImpl {

    public SunkenPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // Sunken Palace enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {1}{U}, {T}, Exile seven cards from your graveyard: Add {U}. When you spend this mana to cast a spell or activate an ability, copy that spell or ability. You may choose new targets for the copy.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(7)));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new SunkenPalaceTriggeredAbility()));
        this.addAbility(ability);
    }

    private SunkenPalace(final SunkenPalace card) {
        super(card);
    }

    @Override
    public SunkenPalace copy() {
        return new SunkenPalace(this);
    }
}

class SunkenPalaceTriggeredAbility extends DelayedTriggeredAbility {

    SunkenPalaceTriggeredAbility() {
        super(new CopyStackObjectEffect("that spell or ability"), Duration.Custom, true, false);
        setTriggerPhrase("When you spend this mana to cast a spell or activate an ability, ");
    }

    private SunkenPalaceTriggeredAbility(final SunkenPalaceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SunkenPalaceTriggeredAbility copy() {
        return new SunkenPalaceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MANA_PAID;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getSourceId())) {
            return false;
        }
        Permanent sourcePermanent = getSourcePermanentOrLKI(game);
        if (sourcePermanent == null
                || sourcePermanent.getAbilities(game).stream()
                    .map(Ability::getOriginalId)
                    .map(UUID::toString)
                    .noneMatch(event.getData()::equals)) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
        if (stackObject == null) {
            return false;
        }
        // trigger on spells and activated abilities (not triggered abilities)
        if (!(stackObject instanceof StackAbility) || ((StackAbility) stackObject).isActivated()) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }
        Player player = game.getPlayer(this.getControllerId());
        return player == null
                || player.getManaPool().getManaItems().stream()
                        .map(ManaPoolItem::getSourceId)
                        .noneMatch(getSourceId()::equals);
    }
}
