/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nick.myers
 */
public final class ForcedMarch extends CardImpl {

    public ForcedMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}{B}");

        // Destroy all creatures with converted mana cost X or less
        this.getSpellAbility().addEffect(new ForcedMarchEffect());
    }

    public ForcedMarch(final ForcedMarch card) {
        super(card);
    }

    @Override
    public ForcedMarch copy() {
        return new ForcedMarch(this);
    }
}

class ForcedMarchEffect extends OneShotEffect {

    public ForcedMarchEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all creatures with converted mana cost X or less";
    }

    public ForcedMarchEffect(final ForcedMarchEffect effect) {
        super(effect);
    }

    @Override
    public ForcedMarchEffect copy() {
        return new ForcedMarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // for(Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(),
                source.getSourceId(),
                game)) {
            if (permanent.getConvertedManaCost() <= source.getManaCostsToPay().getX()) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
