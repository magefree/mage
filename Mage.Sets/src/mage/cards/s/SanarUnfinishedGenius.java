package mage.cards.s;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.SpellsCastWatcher;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SanarUnfinishedGenius extends PrepareCard {

    public SanarUnfinishedGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}", "Wild Idea", new CardType[]{CardType.SORCERY}, "{3}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Sanar enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // {T}: Create a Treasure token. Activate only if you've cast an instant or sorcery spell this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new CreateTokenEffect(new TreasureToken(), 1),
            new TapSourceCost(),
            SanarUnfinishedGeniusCondition.instance
        );
        this.addAbility(ability);

        // Wild Idea
        // Sorcery {3}{U}{R}
        // Search your library for an instant or sorcery card, reveal it, put it into your hand, then shuffle.
        this.getSpellCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY), true
        ));
    }

    private SanarUnfinishedGenius(final SanarUnfinishedGenius card) {
        super(card);
    }

    @Override
    public SanarUnfinishedGenius copy() {
        return new SanarUnfinishedGenius(this);
    }
}

enum SanarUnfinishedGeniusCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null
                && watcher.getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(spell -> spell.isInstantOrSorcery(game));
    }

    @Override
    public String toString() {
        return "you've cast an instant or sorcery spell this turn";
    }
}
