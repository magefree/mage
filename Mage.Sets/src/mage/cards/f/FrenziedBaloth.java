package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrenziedBaloth extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("creature spells");

    public FrenziedBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(filter, Duration.WhileOnBattlefield)));

        // Combat damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(new FrenziedBalothEffect()));
    }

    private FrenziedBaloth(final FrenziedBaloth card) {
        super(card);
    }

    @Override
    public FrenziedBaloth copy() {
        return new FrenziedBaloth(this);
    }
}

class FrenziedBalothEffect extends ContinuousRuleModifyingEffectImpl {

    FrenziedBalothEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "combat damage can't be prevented";
    }

    private FrenziedBalothEffect(final FrenziedBalothEffect effect) {
        super(effect);
    }

    @Override
    public FrenziedBalothEffect copy() {
        return new FrenziedBalothEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return ((PreventDamageEvent) event).isCombatDamage();
    }
}
