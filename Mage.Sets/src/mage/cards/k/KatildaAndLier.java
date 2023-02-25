package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author @stwalsh4118
 */
public final class KatildaAndLier extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Human spell");
    private static final FilterCard graveyardFilter = new FilterCard("instant or sorcery card in your graveyard");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    static {
        graveyardFilter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public KatildaAndLier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Human spell, target instnat or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(new KatildaAndLierEffect(), filter, false);
        ability.addTarget(new TargetCardInYourGraveyard(graveyardFilter));
        this.addAbility(ability);

    }

    private KatildaAndLier(final KatildaAndLier card) {
        super(card);
    }

    @Override
    public KatildaAndLier copy() {
        return new KatildaAndLier(this);
    }
}

class KatildaAndLierEffect extends ContinuousEffectImpl {

    public KatildaAndLierEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost";
    }

    public KatildaAndLierEffect(final KatildaAndLierEffect effect) {
        super(effect);
    }

    @Override
    public KatildaAndLierEffect copy() {
        return new KatildaAndLierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
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
