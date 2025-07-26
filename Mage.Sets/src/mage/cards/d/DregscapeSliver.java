package mage.cards.d;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DregscapeSliver extends CardImpl {

    public DregscapeSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each Sliver creature card in your graveyard has unearth {2}.
        this.addAbility(new SimpleStaticAbility(new DregscapeSliverEffect()));

        // Unearth {2}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}")));
    }

    private DregscapeSliver(final DregscapeSliver card) {
        super(card);
    }

    @Override
    public DregscapeSliver copy() {
        return new DregscapeSliver(this);
    }
}

class DregscapeSliverEffect extends ContinuousEffectImpl {
    DregscapeSliverEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each Sliver creature card in your graveyard has unearth {2}";
    }

    private DregscapeSliverEffect(final DregscapeSliverEffect effect) {
        super(effect);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Card card = (Card) object;
            UnearthAbility ability = new UnearthAbility(new ManaCostsImpl<>("{2}"));
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card == null || !card.isCreature(game) || !card.hasSubtype(SubType.SLIVER, game)) {
                continue;
            }
            affectedObjects.add(card);
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public DregscapeSliverEffect copy() {
        return new DregscapeSliverEffect(this);
    }
}
