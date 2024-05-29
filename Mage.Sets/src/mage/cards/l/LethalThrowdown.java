package mage.cards.l;

import java.util.List;
import java.util.UUID;

import org.checkerframework.checker.units.qual.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author grimreap124
 */
public final class LethalThrowdown extends CardImpl {

    public LethalThrowdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{B}");

        // As an additional cost to cast this spell, sacrifice a creature or sacrifice a modified creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE)
                .setText("sacrifice a creature or sacrifice a modified creature"));
        // Destroy target creature or planeswalker. If the modified creature was sacrificed, draw a card.
        this.getSpellAbility().addEffect(new LethalThrowdownEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private LethalThrowdown(final LethalThrowdown card) {
        super(card);
    }

    @Override
    public LethalThrowdown copy() {
        return new LethalThrowdown(this);
    }
}

class LethalThrowdownEffect extends OneShotEffect {

    LethalThrowdownEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target creature or planeswalker. If the modified creature was sacrificed, draw a card";
    }

    private LethalThrowdownEffect(final LethalThrowdownEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = null;
        boolean isModified = false;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                if (!sacrificeCost.getPermanents().isEmpty()) {
                    sacrificedPermanent = sacrificeCost.getPermanents().get(0);
                    Counters counters = sacrificedPermanent.getCounters(game);
                    if (counters != null && counters.size() > 0) {
                        isModified = true;
                    }
                    List<UUID> attachments = sacrificedPermanent.getAttachments();
                    if (attachments != null && attachments.size() > 0) {
                        isModified = true;
                    }
                }
                break;
            }
        }
        DestroyTargetEffect effect = new DestroyTargetEffect();
        if (isModified) {
            new DrawCardSourceControllerEffect(1).apply(game, source);
        }
        return effect.apply(game, source);
    }

    @Override
    public LethalThrowdownEffect copy() {
        return new LethalThrowdownEffect(this);
    }
}