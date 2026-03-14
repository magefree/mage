package mage.cards.s;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.permanent.token.SpiritBlueToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SmokyLoungeMistySalon extends RoomCard {

    public SmokyLoungeMistySalon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{R}","{3}{U}");

        // Smoky Lounge
        // At the beginning of your first main phase, add {R}{R}. Spend this mana only to cast Room spells and unlock doors.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new AddConditionalManaEffect(Mana.RedMana(2), new SmokyLoungeManaBuilder())
        ));

        // Misty Salon
        // Enchantment -- Room
        // When you unlock this door, create an X/X blue Spirit creature token with flying, where X is the number of unlocked doors among Rooms you control.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new MistySalonEffect(), false, false));

    }

    private SmokyLoungeMistySalon(final SmokyLoungeMistySalon card) {
        super(card);
    }

    @Override
    public SmokyLoungeMistySalon copy() {
        return new SmokyLoungeMistySalon(this);
    }
}

class SmokyLoungeManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SmokyLoungeConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Room spells and unlock doors";
    }
}

class SmokyLoungeConditionalMana extends ConditionalMana {

    public SmokyLoungeConditionalMana(Mana mana) {
        super(mana);
        addCondition(new SmokyLoungeManaCondition());
    }
}

class SmokyLoungeManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        // cast Room spells
        if (source instanceof SpellAbility && !source.isActivated()) {
            MageObject object = game.getObject(source);
            if ((object instanceof StackObject)) {
                return object.hasSubtype(SubType.ROOM, game);
            }
            // checking mana without real cast
            if (game.inCheckPlayableState()) {
                Spell spell = null;
                if (object instanceof Card) {
                    spell = new Spell((Card) object, (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                } else if (object instanceof Commander) {
                    spell = new Spell(((Commander) object).getSourceObject(), (SpellAbility) source, source.getControllerId(), game.getState().getZone(source.getSourceId()), game);
                }
                return spell != null && spell.hasSubtype(SubType.ROOM, game);
            }
        }
        // unlock doors
        return source instanceof RoomUnlockAbility;
    }
}

class MistySalonEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ROOM);

    MistySalonEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X blue Spirit creature token with flying, " +
                "where X is the number of unlocked doors among Rooms you control";
    }

    private MistySalonEffect(final MistySalonEffect effect) {
        super(effect);
    }

    @Override
    public MistySalonEffect copy() {
        return new MistySalonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .mapToInt(permanent -> (permanent.isLeftDoorUnlocked() ? 1 : 0) + (permanent.isRightDoorUnlocked() ? 1 : 0))
                .sum();
        Token token = new SpiritBlueToken();
        token.setPower(count);
        token.setToughness(count);
        return token.putOntoBattlefield(1, game, source);
    }
}
