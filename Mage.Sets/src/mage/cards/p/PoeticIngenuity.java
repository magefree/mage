package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.Dinosaur31Token;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PoeticIngenuity extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DINOSAUR);

    public PoeticIngenuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever one or more Dinosaurs you control attack, create that many Treasure tokens.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new PoeticIngenuityEffect(), 1, filter
        ).setTriggerPhrase("Whenever one or more Dinosaurs you control attack, "));

        // Whenever you cast an artifact spell, create a 3/1 red Dinosaur creature token. This ability triggers only once per turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new Dinosaur31Token()),
                StaticFilters.FILTER_SPELL_AN_ARTIFACT,
                false
        ).setTriggersOnceEachTurn(true));
    }

    private PoeticIngenuity(final PoeticIngenuity card) {
        super(card);
    }

    @Override
    public PoeticIngenuity copy() {
        return new PoeticIngenuity(this);
    }
}

class PoeticIngenuityEffect extends OneShotEffect {

    PoeticIngenuityEffect() {
        super(Outcome.Benefit);
        staticText = "create that many Treasure tokens";
    }

    private PoeticIngenuityEffect(final PoeticIngenuityEffect effect) {
        super(effect);
    }

    @Override
    public PoeticIngenuityEffect copy() {
        return new PoeticIngenuityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue(AttacksWithCreaturesTriggeredAbility.VALUEKEY_NUMBER_ATTACKERS);
        return amount > 0 &&
                new CreateTokenEffect(new TreasureToken(), amount).apply(game, source);
    }
}