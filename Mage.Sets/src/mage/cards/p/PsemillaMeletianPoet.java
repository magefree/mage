package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.NymphToken;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PsemillaMeletianPoet extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledEnchantmentPermanent("you control five or more enchantments"),
            ComparisonType.MORE_THAN, 4
    );
    private static final Hint hint = new ValueHint(
            "Enchantments you control", new PermanentsOnBattlefieldCount(new FilterControlledEnchantmentPermanent())
    );

    public PsemillaMeletianPoet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast your first enchantment spell each turn, create a 2/2 white Nymph enchantment creature token.
        this.addAbility(new PsemillaMeletianPoetTriggeredAbility());

        // At the beginning of each combat, if you control five or more enchantments, Psemilla, Meletian Poet gets +4/+4 and gains lifelink until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                TargetController.ANY,
                new BoostSourceEffect(4, 4, Duration.EndOfTurn)
                        .setText("{this} gets +4/+4"),
                false
        ).withInterveningIf(condition);
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains lifelink until end of turn"));
        this.addAbility(ability.addHint(hint));
    }

    private PsemillaMeletianPoet(final PsemillaMeletianPoet card) {
        super(card);
    }

    @Override
    public PsemillaMeletianPoet copy() {
        return new PsemillaMeletianPoet(this);
    }
}

class PsemillaMeletianPoetTriggeredAbility extends TriggeredAbilityImpl {

    PsemillaMeletianPoetTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new NymphToken()));
        setTriggerPhrase("Whenever you cast your first enchantment spell each turn, ");
    }

    private PsemillaMeletianPoetTriggeredAbility(final PsemillaMeletianPoetTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsemillaMeletianPoetTriggeredAbility copy() {
        return new PsemillaMeletianPoetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isEnchantment(game)) {
            return false;
        }
        List<Spell> spells = game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(getControllerId())
                .stream()
                .filter(s -> s.isEnchantment(game))
                .collect(Collectors.toList());
        return spells.size() == 1 && spells.get(0).getId().equals(event.getTargetId());
    }
}
