package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class AlexWilderRunaway extends CardImpl {

    public AlexWilderRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Alex Wilder or another creature you control enters, if you cast it from anywhere other than your hand, it gets +2/+0 and gains haste until end of turn.
        Ability ability = new AlexWilderRunawayTriggeredAbility();
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
            .setText("and gains haste until end of turn"));
        this.addAbility(ability);

        // Escape--{2}{R}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{R}", 3));
    }

    private AlexWilderRunaway(final AlexWilderRunaway card) {
        super(card);
    }

    @Override
    public AlexWilderRunaway copy() {
        return new AlexWilderRunaway(this);
    }
}

class AlexWilderRunawayTriggeredAbility extends EntersBattlefieldThisOrAnotherTriggeredAbility {

    AlexWilderRunawayTriggeredAbility() {
        super(
            new BoostTargetEffect(2, 0).setText("it gets +2/+0"),
            StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED,
            false,
            SetTargetPointer.PERMANENT,
            true
        );
        setTriggerPhrase("Whenever {this} or another creature you control enters, if you cast it from anywhere other than your hand, ");
    }

    private AlexWilderRunawayTriggeredAbility(final AlexWilderRunawayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AlexWilderRunawayTriggeredAbility copy() {
        return new AlexWilderRunawayTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent entersEvent = (EntersTheBattlefieldEvent) event;
        if (entersEvent == null) {
            return false;
        }
        Permanent permanent = entersEvent.getTarget();
        if (permanent == null || entersEvent.getFromZone() != Zone.STACK) {
            return false;
        }

        Spell spell = game.getSpellOrLKIStack(permanent.getId());
        return spell != null
            && spell.getFromZone() != Zone.HAND
            && super.checkTrigger(event, game);
    }
}
