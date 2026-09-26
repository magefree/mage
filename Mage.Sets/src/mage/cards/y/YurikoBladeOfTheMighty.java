package mage.cards.y;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class YurikoBladeOfTheMighty extends CardImpl {

    public YurikoBladeOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // During combat, players can't cast spells or activate abilities that aren't mana abilities.
        this.addAbility(new SimpleStaticAbility(new YurikoBladeOfTheMightyEffect()));

        // Whenever a creature you control attacks a player alone, it gains double strike until end of turn.
        Ability ability = new AttacksPlayerAloneControlledTriggeredAbility(
            new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)
        );
        this.addAbility(ability);
    }

    private YurikoBladeOfTheMighty(final YurikoBladeOfTheMighty card) {
        super(card);
    }

    @Override
    public YurikoBladeOfTheMighty copy() {
        return new YurikoBladeOfTheMighty(this);
    }
}

class YurikoBladeOfTheMightyEffect extends ContinuousRuleModifyingEffectImpl {

    YurikoBladeOfTheMightyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "During combat, players can't cast instant spells or activate abilities that aren't mana abilities";
    }

    private YurikoBladeOfTheMightyEffect(final YurikoBladeOfTheMightyEffect effect) {
        super(effect);
    }

    @Override
    public YurikoBladeOfTheMightyEffect copy() {
        return new YurikoBladeOfTheMightyEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "During combat, players can't cast instant spells or activate abilities that aren't mana abilities (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnPhaseType() == TurnPhase.COMBAT) {
            MageObject object = game.getObject(event.getSourceId());
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                if (object != null && object.isInstant(game)) {
                    return true;
                }
            }
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                return ability.isPresent() && !ability.get().isManaActivatedAbility();
            }
        }
        return false;
    }
}
