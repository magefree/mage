package mage.cards.j;

import java.util.Objects;
import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jimga150
 */
public final class JurassicPark extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Dinosaur you control");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Number of Dinosaurs you control", new PermanentsOnBattlefieldCount(filter)
    );

    public JurassicPark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.nightCard = true;

        // (Transforms from Welcome to ....)
        // Each Dinosaur card in your graveyard has escape. The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.
        // Based on Underworld Breach
        this.addAbility(new SimpleStaticAbility(new JurassicParkEffect()));

        // {T}: Add {G} for each Dinosaur you control.
        // Based on Gaea's Cradle
        DynamicManaAbility ability = new DynamicManaAbility(
                Mana.GreenMana(1),
                new PermanentsOnBattlefieldCount(filter)
        );
        this.addAbility(ability.addHint(hint));
    }

    private JurassicPark(final JurassicPark card) {
        super(card);
    }

    @Override
    public JurassicPark copy() {
        return new JurassicPark(this);
    }
}

class JurassicParkEffect extends ContinuousEffectImpl {

    JurassicParkEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each Dinosaur card in your graveyard has escape. " +
                "The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.";
    }

    private JurassicParkEffect(final JurassicParkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.getManaCost().getText().isEmpty()) // card must have a mana cost
                .filter(card -> card.getSubtype().contains(SubType.DINOSAUR))
                .forEach(card -> {
                    Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 3);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }

    @Override
    public JurassicParkEffect copy() {
        return new JurassicParkEffect(this);
    }
}
