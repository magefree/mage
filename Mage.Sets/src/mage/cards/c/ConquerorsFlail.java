package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class ConquerorsFlail extends CardImpl {

    public ConquerorsFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each color among permanents you control.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(
                ConquerorsFlailColorCount.instance, ConquerorsFlailColorCount.instance, Duration.WhileOnBattlefield
        )));

        // As long as Conqueror's Flail is attached to a creature, your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new ConquerorsFlailEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private ConquerorsFlail(final ConquerorsFlail card) {
        super(card);
    }

    @Override
    public ConquerorsFlail copy() {
        return new ConquerorsFlail(this);
    }
}

enum ConquerorsFlailColorCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ObjectColor color = new ObjectColor("");
        game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .map(permanent -> permanent.getColor(game))
                .forEach(color::addColor);
        return color.getColorCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "color among permanents you control";
    }

    @Override
    public ConquerorsFlailColorCount copy() {
        return this;
    }
}

class ConquerorsFlailEffect extends ContinuousRuleModifyingEffectImpl {

    public ConquerorsFlailEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as {this} is attached to a creature, your opponents can't cast spells during your turn";
    }

    public ConquerorsFlailEffect(final ConquerorsFlailEffect effect) {
        super(effect);
    }

    @Override
    public ConquerorsFlailEffect copy() {
        return new ConquerorsFlailEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.isActivePlayer(source.getControllerId())
                && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())
                && Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> permanent.isCreature(game))
                .orElse(false);
    }
}
