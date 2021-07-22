package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GilanraCallerOfWirewood extends CardImpl {

    public GilanraCallerOfWirewood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        BasicManaAbility ability = new GreenManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new GilanraCallerOfWirewoodTriggeredAbility()));
        ability.setUndoPossible(false);
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private GilanraCallerOfWirewood(final GilanraCallerOfWirewood card) {
        super(card);
    }

    @Override
    public GilanraCallerOfWirewood copy() {
        return new GilanraCallerOfWirewood(this);
    }
}

class GilanraCallerOfWirewoodTriggeredAbility extends DelayedTriggeredAbility {

    GilanraCallerOfWirewoodTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.Custom, true, false);
    }

    private GilanraCallerOfWirewoodTriggeredAbility(final GilanraCallerOfWirewoodTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GilanraCallerOfWirewoodTriggeredAbility copy() {
        return new GilanraCallerOfWirewoodTriggeredAbility(this);
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
                || sourcePermanent
                .getAbilities(game)
                .stream()
                .map(Ability::getOriginalId)
                .map(UUID::toString)
                .noneMatch(event.getData()::equals)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getManaValue() >= 6;
    }

    @Override
    public boolean isInactive(Game game) {
        if (super.isInactive(game)) {
            return true;
        }
        // must remove effect on empty mana pool to fix accumulate bug
        // if no mana in pool then it can be discarded
        Player player = game.getPlayer(this.getControllerId());
        return player == null
                || player
                .getManaPool()
                .getManaItems()
                .stream()
                .map(ManaPoolItem::getSourceId)
                .noneMatch(getSourceId()::equals);
    }

    @Override
    public String getRule() {
        return "When you spend this mana to cast a spell with mana value 6 or greater, draw a card.";
    }
}
