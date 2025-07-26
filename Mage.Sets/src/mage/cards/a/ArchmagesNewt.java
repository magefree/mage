package mage.cards.a;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.SaddledCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchmagesNewt extends CardImpl {

    public ArchmagesNewt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Archmage's Newt deals combat damage to a player, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost. That card gains flashback {0} until end of turn instead if Archmage's Newt is saddled.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ArchmagesNewtEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private ArchmagesNewt(final ArchmagesNewt card) {
        super(card);
    }

    @Override
    public ArchmagesNewt copy() {
        return new ArchmagesNewt(this);
    }
}

class ArchmagesNewtEffect extends ContinuousEffectImpl {

    private boolean saddled = false;

    ArchmagesNewtEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. " +
                "The flashback cost is equal to its mana cost. That card gains flashback {0} until end of turn instead if {this} is saddled";
    }

    private ArchmagesNewtEffect(final ArchmagesNewtEffect effect) {
        super(effect);
        this.saddled = effect.saddled;
    }

    @Override
    public ArchmagesNewtEffect copy() {
        return new ArchmagesNewtEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.saddled = SaddledCondition.instance.apply(game, source);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Card card = (Card) object;

            FlashbackAbility ability;
            if (saddled) {
                ability = new FlashbackAbility(card, new GenericManaCost(0));
            } else {
                ability = new FlashbackAbility(card, card.getManaCost());
            }
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());

            game.getState().addOtherAbility(card, ability);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        affectedObjects.add(card);
        return true;
    }
}
