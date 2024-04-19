package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Pronoun;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RalMonsoonMage extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");

    public RalMonsoonMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = RalLeylineProdigy.class;

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell during your turn, flip a coin. If you lose the flip, Ral, Monsoon Mage deals 1 damage to you. If you win the flip, you may exile Ral. If you do, return him to the battlefield transformed under his owner control.
        this.addAbility(new TransformAbility());
        this.addAbility(new RalMonsoonMageTriggeredAbility());
    }

    private RalMonsoonMage(final RalMonsoonMage card) {
        super(card);
    }

    @Override
    public RalMonsoonMage copy() {
        return new RalMonsoonMage(this);
    }
}

class RalMonsoonMageTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RalMonsoonMageTriggeredAbility() {
        super(new RalMonsoonMageEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false);
        setTriggerPhrase("Whenever you cast an instant or sorcery spell during your turn, ");
    }

    private RalMonsoonMageTriggeredAbility(final RalMonsoonMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RalMonsoonMageTriggeredAbility copy() {
        return new RalMonsoonMageTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && super.checkTrigger(event, game);
    }
}

class RalMonsoonMageEffect extends OneShotEffect {

    RalMonsoonMageEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin. If you lose the flip, {this} deals 1 damage to you. " +
                "If you win the flip, you may exile {this}. If you do, return him to the battlefield transformed under his owner control";
    }

    private RalMonsoonMageEffect(final RalMonsoonMageEffect effect) {
        super(effect);
    }

    @Override
    public RalMonsoonMageEffect copy() {
        return new RalMonsoonMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean wonFlip = player.flipCoin(source, game, true);
        if (wonFlip) {
            if (player.chooseUse(outcome, "Exile {this} and return transformed?", source, game)) {
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE)
                        .apply(game, source);
            }
        } else {
            new DamageControllerEffect(1)
                    .apply(game, source);
        }
        return true;
    }
}

