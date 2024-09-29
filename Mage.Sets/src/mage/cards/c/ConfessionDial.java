package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Cguy7777
 */
public final class ConfessionDial extends CardImpl {

    private static final FilterCreatureCard filter
            = new FilterCreatureCard("legendary creature card in your graveyard");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public ConfessionDial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // When Confession Dial enters the battlefield, surveil 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(3, false)));

        // {T}: Target legendary creature card in your graveyard gains escape until end of turn.
        // The escape cost is equal to its mana cost plus exile three other cards from your graveyard.
        Ability ability = new SimpleActivatedAbility(new ConfessionDialEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ConfessionDial(final ConfessionDial card) {
        super(card);
    }

    @Override
    public ConfessionDial copy() {
        return new ConfessionDial(this);
    }
}

class ConfessionDialEffect extends ContinuousEffectImpl {

    ConfessionDialEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Target legendary creature card in your graveyard gains escape until end of turn. " +
                "The escape cost is equal to its mana cost plus exile three other cards from your graveyard. " +
                "<i>(You may cast it from your graveyard for its escape cost this turn.)</i>";
    }

    private ConfessionDialEffect(final ConfessionDialEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card == null || card.getManaCost().getText().isEmpty()) {
            return false;
        }
        Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 3);
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);
        return true;
    }

    @Override
    public ConfessionDialEffect copy() {
        return new ConfessionDialEffect(this);
    }
}
