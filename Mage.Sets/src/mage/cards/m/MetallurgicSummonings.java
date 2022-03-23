
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.MetallurgicSummoningsConstructToken;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MetallurgicSummonings extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public MetallurgicSummonings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever you cast an instant or sorcery spell, create an X/X colorless Construct artifact creature token, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(new MetallurgicSummoningsTokenEffect(), filter, false, true));

        // {3}{U}{U}, Exile Metallurgic Summons: Return all instant and sorcery cards from your graveyard to your hand. Activate this ability only if you control six or more artifacts.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new MetallurgicSummoningsReturnEffect(), new ManaCostsImpl("{3}{U}{U}"),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent(), ComparisonType.MORE_THAN, 5),
                "{3}{U}{U}, Exile {this}: Return all instant and sorcery cards from your graveyard to your hand."
                + " Activate only if you control six or more artifacts.");
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private MetallurgicSummonings(final MetallurgicSummonings card) {
        super(card);
    }

    @Override
    public MetallurgicSummonings copy() {
        return new MetallurgicSummonings(this);
    }
}

class MetallurgicSummoningsTokenEffect extends OneShotEffect {

    public MetallurgicSummoningsTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X colorless Construct artifact creature token, where X is that spell's mana value";
    }

    public MetallurgicSummoningsTokenEffect(MetallurgicSummoningsTokenEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            int cmc = spell.getManaValue();
            if (cmc > 0) {
                return new CreateTokenEffect(new MetallurgicSummoningsConstructToken(cmc)).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public MetallurgicSummoningsTokenEffect copy() {
        return new MetallurgicSummoningsTokenEffect(this);
    }
}

class MetallurgicSummoningsReturnEffect extends OneShotEffect {

    MetallurgicSummoningsReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return all instant and sorcery cards from your graveyard to your hand. Activate only if you control six or more artifacts";
    }

    MetallurgicSummoningsReturnEffect(final MetallurgicSummoningsReturnEffect effect) {
        super(effect);
    }

    @Override
    public MetallurgicSummoningsReturnEffect copy() {
        return new MetallurgicSummoningsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.moveCards(controller.getGraveyard().getCards(new FilterInstantOrSorceryCard(),
                    source.getControllerId(), source, game), Zone.HAND, source, game);
        }
        return false;
    }
}
