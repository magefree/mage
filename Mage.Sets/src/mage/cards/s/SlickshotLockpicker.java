package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.PlotAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SlickshotLockpicker extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card in your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public SlickshotLockpicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Slickshot Lockpicker enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SlickshotLockpickerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Plot {2}{U}
        this.addAbility(new PlotAbility("{2}{U}"));
    }

    private SlickshotLockpicker(final SlickshotLockpicker card) {
        super(card);
    }

    @Override
    public SlickshotLockpicker copy() {
        return new SlickshotLockpicker(this);
    }
}

/**
 * From {@link mage.cards.s.SnapcasterMage SnapcasterMage}
 */
class SlickshotLockpickerEffect extends ContinuousEffectImpl {

    SlickshotLockpickerEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. "
                + "The flashback cost is equal to its mana cost";
    }

    private SlickshotLockpickerEffect(final SlickshotLockpickerEffect effect) {
        super(effect);
    }

    @Override
    public SlickshotLockpickerEffect copy() {
        return new SlickshotLockpickerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
            return true;
        }
        return false;
    }
}
