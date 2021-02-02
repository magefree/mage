package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ForetellSourceControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class DreamDevourer extends CardImpl {

    public DreamDevourer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by 2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DreamDevourerAddAbilityEffect()));

        // Whenever you foretell a card, Dream Devourer gets +2/+0 until end of turn.
        this.addAbility(new ForetellSourceControllerTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));

    }

    private DreamDevourer(final DreamDevourer card) {
        super(card);
    }

    @Override
    public DreamDevourer copy() {
        return new DreamDevourer(this);
    }
}

class DreamDevourerAddAbilityEffect extends ContinuousEffectImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    static {
        filter.add(Predicates.not(new AbilityPredicate(ForetellAbility.class)));
    }

    DreamDevourerAddAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each nonland card in your hand without foretell has foretell. Its foretell cost is equal to its mana cost reduced by {2}";
    }

    private DreamDevourerAddAbilityEffect(final DreamDevourerAddAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DreamDevourerAddAbilityEffect copy() {
        return new DreamDevourerAddAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(filter, game)) {
            String costText = CardUtil.reduceCost(card.getSpellAbility().getManaCostsToPay(), 2).getText();
            game.getState().setValue(card.getId().toString() + "Foretell Cost", costText);
            ForetellAbility foretellAbility = new ForetellAbility(card, costText);
            foretellAbility.setSourceId(card.getId());
            foretellAbility.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, foretellAbility);
        }
        return true;
    }
}
