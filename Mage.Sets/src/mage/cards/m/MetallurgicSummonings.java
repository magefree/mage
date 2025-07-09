package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.token.MetallurgicSummoningsConstructToken;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MetallurgicSummonings extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control six or more artifacts"),
            ComparisonType.MORE_THAN, 5
    );

    public MetallurgicSummonings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever you cast an instant or sorcery spell, create an X/X colorless Construct artifact creature token, where X is that spell's converted mana cost.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new MetallurgicSummoningsTokenEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {3}{U}{U}, Exile Metallurgic Summons: Return all instant and sorcery cards from your graveyard to your hand. Activate this ability only if you control six or more artifacts.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new MetallurgicSummoningsReturnEffect(), new ManaCostsImpl<>("{3}{U}{U}"), condition
        );
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

    MetallurgicSummoningsTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create an X/X colorless Construct artifact creature token, where X is that spell's mana value";
    }

    private MetallurgicSummoningsTokenEffect(final MetallurgicSummoningsTokenEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int mv = Optional
                .ofNullable((Spell) getValue("spellCast"))
                .map(Spell::getManaValue)
                .orElse(0);
        return new MetallurgicSummoningsConstructToken(mv).putOntoBattlefield(1, game, source);
    }

    @Override
    public MetallurgicSummoningsTokenEffect copy() {
        return new MetallurgicSummoningsTokenEffect(this);
    }
}

class MetallurgicSummoningsReturnEffect extends OneShotEffect {

    MetallurgicSummoningsReturnEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return all instant and sorcery cards from your graveyard to your hand";
    }

    private MetallurgicSummoningsReturnEffect(final MetallurgicSummoningsReturnEffect effect) {
        super(effect);
    }

    @Override
    public MetallurgicSummoningsReturnEffect copy() {
        return new MetallurgicSummoningsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.moveCards(
                controller.getGraveyard().getCards(
                        StaticFilters.FILTER_CARDS_INSTANT_AND_SORCERY,
                        source.getControllerId(), source, game
                ), Zone.HAND, source, game
        );
    }
}
