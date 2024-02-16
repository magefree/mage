package mage.cards.n;

import mage.MageInt;
import mage.Mana;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * This is the MTGO variant of the card
 * <a href="https://www.mtgo.com/news/mtgo-woe-8292023#unfinity">Original announcement</a>
 * <a href="https://scryfall.com/card/unf/107m/name-sticker-goblin">Scryfall link</a>
 * @author notgreat
 */
public final class NameStickerGoblin extends CardImpl {

    public NameStickerGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.GUEST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect(20, "roll a 20-sided die");
        // When this creature enters the battlefield from anywhere other than a graveyard or exile, if it’s
        // on the battlefield and you control 9 or fewer creatures named “Name Sticker” Goblin, roll a 20-sided die.
        // 1-6 | Add {R}{R}{R}{R}.
        effect.addTableEntry(1, 6, new BasicManaEffect(Mana.RedMana(4)));
        // 7-14 | Add {R}{R}{R}{R}{R}.
        effect.addTableEntry(7, 14, new BasicManaEffect(Mana.RedMana(5)));
        // 15-20 | Add {R}{R}{R}{R}{R}{R}.
        effect.addTableEntry(15, 20, new BasicManaEffect(Mana.RedMana(6)));
        this.addAbility(new NameStickerGoblinTriggeredAbility(effect));
    }

    private NameStickerGoblin(final NameStickerGoblin card) {
        super(card);
    }

    @Override
    public NameStickerGoblin copy() {
        return new NameStickerGoblin(this);
    }
}

class NameStickerGoblinTriggeredAbility extends TriggeredAbilityImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    static{
        filter.add(new NamePredicate("\"Name Sticker\" Goblin"));
    }
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_LESS, 9);
    NameStickerGoblinTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("When this creature enters the battlefield from anywhere other than a graveyard or exile, if it's on the battlefield and you control 9 or fewer creatures named \"Name Sticker\" Goblin, ");
    }

    private NameStickerGoblinTriggeredAbility(final NameStickerGoblinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NameStickerGoblinTriggeredAbility copy() {
        return new NameStickerGoblinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent zEvent = (EntersTheBattlefieldEvent) event;
        if (zEvent == null) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (permanent == null) {
            return false;
        }
        Zone zone = zEvent.getFromZone();
        return zone != Zone.GRAVEYARD && zone != Zone.EXILED
                && permanent.getId().equals(getSourceId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && condition.apply(game, this);
    }
}
