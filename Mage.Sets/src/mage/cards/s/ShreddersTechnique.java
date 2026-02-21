package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class ShreddersTechnique extends CardImpl {

    public ShreddersTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Sneak {B}
        this.addAbility(new SneakAbility(this, "{B}"));

        // Destroy target creature or enchantment. If an enchantment was destroyed this way, you lose 2 life.
        this.getSpellAbility().addEffect(new ShreddersTechniqueEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
    }

    private ShreddersTechnique(final ShreddersTechnique card) {
        super(card);
    }

    @Override
    public ShreddersTechnique copy() {
        return new ShreddersTechnique(this);
    }
}

class ShreddersTechniqueEffect extends OneShotEffect {

    ShreddersTechniqueEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy target creature or enchantment. If an enchantment was destroyed this way, you lose 2 life.";
    }

    private ShreddersTechniqueEffect(final ShreddersTechniqueEffect effect) {
        super (effect);
    }

    @Override
    public ShreddersTechniqueEffect copy() {
        return new ShreddersTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        if (!permanent.destroy(source, game, false)){
            return false;
        }

        if (permanent.isEnchantment(game)){
            new LoseLifeSourceControllerEffect(2).apply(game, source);
        }
        return true;
    }
}
