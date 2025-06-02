package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongcrafterMage extends CardImpl {

    public SongcrafterMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, target instant or sorcery card in your graveyard gains harmonize until end of turn. Its harmonize cost is equal to its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SongcrafterMageEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private SongcrafterMage(final SongcrafterMage card) {
        super(card);
    }

    @Override
    public SongcrafterMage copy() {
        return new SongcrafterMage(this);
    }
}

class SongcrafterMageEffect extends ContinuousEffectImpl {

    SongcrafterMageEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains harmonize until end of turn. " +
                "Its harmonize cost is equal to its mana cost";
    }

    private SongcrafterMageEffect(final SongcrafterMageEffect effect) {
        super(effect);
    }

    @Override
    public SongcrafterMageEffect copy() {
        return new SongcrafterMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null) {
            return false;
        }
        Ability ability = new HarmonizeAbility(card, card.getManaCost().getText());
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);
        return true;
    }
}
