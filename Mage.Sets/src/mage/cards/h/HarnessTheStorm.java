package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HarnessTheStorm extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with the same name as that spell from your graveyard");

    static {
        filter.add(HarnessTheStormPredicate.instance);
    }

    public HarnessTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast an instant or sorcery spell from your hand, you may cast target card with the same name as that spell from your graveyard.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new HarnessTheStormEffect(),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.NONE, Zone.HAND
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private HarnessTheStorm(final HarnessTheStorm card) {
        super(card);
    }

    @Override
    public HarnessTheStorm copy() {
        return new HarnessTheStorm(this);
    }

}

enum HarnessTheStormPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getSource()
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("spellCast"))
                .map(Spell.class::cast)
                .findFirst()
                .map(spell -> spell.sharesName(input.getObject(), game))
                .orElse(false);
    }
}

class HarnessTheStormEffect extends OneShotEffect {

    HarnessTheStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast target card with the same name as that "
                + "spell from your graveyard. <i>(You still pay its costs.)</i>";
    }

    private HarnessTheStormEffect(final HarnessTheStormEffect effect) {
        super(effect);
    }

    @Override
    public HarnessTheStormEffect copy() {
        return new HarnessTheStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return controller != null && card != null
                && CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
    }
}
