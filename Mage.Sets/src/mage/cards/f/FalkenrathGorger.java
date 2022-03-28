
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FalkenrathGorger extends CardImpl {

    public FalkenrathGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        /**
         * 4/8/2016 Falkenrath Gorger's ability only applies while it's on the
         * battlefield. If you discard it, it won't give itself madness.
         * 4/8/2016 If Falkenrath Gorger leaves the battlefield before the
         * madness trigger has resolved for a Vampire card that gained madness
         * with its ability, the madness ability will still let you cast that
         * Vampire card for the appropriate cost even though it no longer has
         * madness. 4/8/2016 If you discard a Vampire creature card that already
         * has a madness ability, you'll choose which madness ability exiles it.
         * You may choose either the one it normally has or the one it gains
         * from Falkenrath Gorger.
         */
        // Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FalkenrathGorgerEffect()));
    }

    private FalkenrathGorger(final FalkenrathGorger card) {
        super(card);
    }

    @Override
    public FalkenrathGorger copy() {
        return new FalkenrathGorger(this);
    }
}

class FalkenrathGorgerEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Vampire creature card you own");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());

    }

    Map<UUID, MadnessAbility> madnessAbilities = new HashMap<>(); // reuse the same ability for the same object

    public FalkenrathGorgerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Each Vampire creature card you own that isn't on the battlefield has madness. The madness cost is equal to its mana cost";
    }

    public FalkenrathGorgerEffect(final FalkenrathGorgerEffect effect) {
        super(effect);
        this.madnessAbilities.putAll(effect.madnessAbilities);
    }

    @Override
    public FalkenrathGorgerEffect copy() {
        return new FalkenrathGorgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, MadnessAbility> usedMadnessAbilities = new HashMap<>();
            // hand
            for (Card card : controller.getHand().getCards(filter, game)) {
                addMadnessToCard(game, card, usedMadnessAbilities);
            }
            // graveyard
            for (Card card : controller.getGraveyard().getCards(filter, game)) {
                addMadnessToCard(game, card, usedMadnessAbilities);
            }
            // Exile
            for (Card card : game.getExile().getAllCards(game)) {
                if (filter.match(card, controller.getId(), source, game)) {
                    if (card.isOwnedBy(controller.getId())) {
                        addMadnessToCard(game, card, usedMadnessAbilities);
                    }
                }
            }
            madnessAbilities.clear();
            madnessAbilities.putAll(usedMadnessAbilities);
            return true;
        }

        return false;
    }

    private void addMadnessToCard(Game game, Card card, Map<UUID, MadnessAbility> usedMadnessAbilities) {
        MadnessAbility ability = madnessAbilities.get(card.getId());
        if (ability == null) {
            ability = new MadnessAbility(card, card.getSpellAbility().getManaCosts());
        }
        game.getState().addOtherAbility(card, ability, false);
        usedMadnessAbilities.put(card.getId(), ability);
    }
}
