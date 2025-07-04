
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 * modified tiera3 - added Hint
 */
public final class GalvanicBombardment extends CardImpl {

    private static final FilterCard filter = new FilterCard("2 plus the number of cards named Galvanic Bombardment");

    static {
        filter.add(new NamePredicate("Galvanic Bombardment"));
    }

    private static final Hint hint = new ValueHint(
            "Cards named Galvanic Bombardment in your graveyard", new GalvanicBombardmentCardsInControllerGraveyardCount(filter)
    );

    public GalvanicBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Galvanic Bombardment deals X damage to target creature, where X is 2 plus the number of cards named Galvanic Bombardment in your graveyard.
        Effect effect = new DamageTargetEffect(new GalvanicBombardmentCardsInControllerGraveyardCount(filter));
        effect.setText("{this} deals X damage to target creature, where X is 2 plus the number of cards named Galvanic Bombardment in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private GalvanicBombardment(final GalvanicBombardment card) {
        super(card);
    }

    @Override
    public GalvanicBombardment copy() {
        return new GalvanicBombardment(this);
    }
}

class GalvanicBombardmentCardsInControllerGraveyardCount implements DynamicValue {

    private final FilterCard filter;

    public GalvanicBombardmentCardsInControllerGraveyardCount(FilterCard filter) {
        this.filter = filter;
    }

    private GalvanicBombardmentCardsInControllerGraveyardCount(GalvanicBombardmentCardsInControllerGraveyardCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            amount += controller.getGraveyard().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
        }
        return amount + 2;
    }

    @Override
    public GalvanicBombardmentCardsInControllerGraveyardCount copy() {
        return new GalvanicBombardmentCardsInControllerGraveyardCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return filter.getMessage() + " in your graveyard";
    }
}
