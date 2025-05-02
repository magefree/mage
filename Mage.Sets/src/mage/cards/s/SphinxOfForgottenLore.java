package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
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
 *
 * @author ciaccona007
 */
public final class SphinxOfForgottenLore extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card in your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public SphinxOfForgottenLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to that card's mana cost.
        Ability ability = new AttacksTriggeredAbility(new SphinxOfForgottenLoreEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SphinxOfForgottenLore(final SphinxOfForgottenLore card) {
        super(card);
    }

    @Override
    public SphinxOfForgottenLore copy() {
        return new SphinxOfForgottenLore(this);
    }
}

class SphinxOfForgottenLoreEffect extends ContinuousEffectImpl {

    SphinxOfForgottenLoreEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. "
                + "The flashback cost is equal to that card's mana cost";
    }

    private SphinxOfForgottenLoreEffect(final SphinxOfForgottenLoreEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfForgottenLoreEffect copy() {
        return new SphinxOfForgottenLoreEffect(this);
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
