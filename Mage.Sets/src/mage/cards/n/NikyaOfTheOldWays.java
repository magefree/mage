package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NikyaOfTheOldWays extends CardImpl {

    public NikyaOfTheOldWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // You can't cast noncreature spells.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, new NikyaOfTheOldWaysCantCastEffect()
        ));

        // Whenever you tap a land for mana, add one mana of any type that land produced.
        AddManaOfAnyTypeProducedEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana of any type that land produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect,
                new FilterControlledLandPermanent("you tap a land"),
                SetTargetPointer.PERMANENT)
        );
    }

    private NikyaOfTheOldWays(final NikyaOfTheOldWays card) {
        super(card);
    }

    @Override
    public NikyaOfTheOldWays copy() {
        return new NikyaOfTheOldWays(this);
    }
}

class NikyaOfTheOldWaysCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    NikyaOfTheOldWaysCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast noncreature spells";
    }

    private NikyaOfTheOldWaysCantCastEffect(final NikyaOfTheOldWaysCantCastEffect effect) {
        super(effect);
    }

    @Override
    public NikyaOfTheOldWaysCantCastEffect copy() {
        return new NikyaOfTheOldWaysCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Card card = game.getCard(event.getSourceId());
            return card != null && !card.isCreature(game);
        }
        return false;
    }

}