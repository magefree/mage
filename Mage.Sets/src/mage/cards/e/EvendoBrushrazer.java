package mage.cards.e;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.util.CardUtil;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvendoBrushrazer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public EvendoBrushrazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice a nontoken permanent, exile the top card of your library.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new ExileCardsFromTopOfLibraryControllerEffect(1, true), filter
        ));

        // During your turn, as long as you've sacrificed a nontoken permanent this turn, you may play cards exiled with this creature.
        this.addAbility(new SimpleStaticAbility(new EvendoBrushrazerEffect()), new PermanentsSacrificedWatcher());

        // {T}, Sacrifice a land: Add {R}{R}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        this.addAbility(ability);
    }

    private EvendoBrushrazer(final EvendoBrushrazer card) {
        super(card);
    }

    @Override
    public EvendoBrushrazer copy() {
        return new EvendoBrushrazer(this);
    }
}

class EvendoBrushrazerEffect extends AsThoughEffectImpl {

    EvendoBrushrazerEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "during your turn, as long as you've sacrificed a nontoken " +
                "permanent this turn, you may play cards exiled with this creature";
    }

    private EvendoBrushrazerEffect(final EvendoBrushrazerEffect effect) {
        super(effect);
    }

    @Override
    public EvendoBrushrazerEffect copy() {
        return new EvendoBrushrazerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && game.isActivePlayer(affectedControllerId)
                && !game
                .getState()
                .getWatcher(PermanentsSacrificedWatcher.class)
                .getThisTurnSacrificedPermanents(affectedControllerId)
                .stream()
                .allMatch(PermanentToken.class::isInstance)
                && game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, source.getSourceId(),
                        game.getState().getZoneChangeCounter(source.getSourceId())
                ))
                .contains(CardUtil.getMainCardId(game, objectId));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
