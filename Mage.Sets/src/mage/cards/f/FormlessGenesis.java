package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ShapeshifterDeathtouchToken;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FormlessGenesis extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Lands in your graveyard", new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND)
    );

    public FormlessGenesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{2}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Create an X/X colorless Shapeshifter creature token with changeling and deathtouch, where X is the number of land cards in your graveyard.
        this.getSpellAbility().addEffect(new FormlessGenesisEffect());
        this.getSpellAbility().addHint(hint);

        // Retrace
        this.addAbility(new RetraceAbility(this));
    }

    private FormlessGenesis(final FormlessGenesis card) {
        super(card);
    }

    @Override
    public FormlessGenesis copy() {
        return new FormlessGenesis(this);
    }
}

class FormlessGenesisEffect extends OneShotEffect {

    FormlessGenesisEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X colorless Shapeshifter creature token with changeling and deathtouch, " +
                "where X is the number of land cards in your graveyard";
    }

    private FormlessGenesisEffect(final FormlessGenesisEffect effect) {
        super(effect);
    }

    @Override
    public FormlessGenesisEffect copy() {
        return new FormlessGenesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .map(Player::getGraveyard)
                .map(g -> g.count(StaticFilters.FILTER_CARD_LAND, game))
                .orElse(0);
        return new ShapeshifterDeathtouchToken(amount).putOntoBattlefield(1, game, source);
    }
}
