package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PrismariToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaOpus extends CardImpl {

    public MagmaOpus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{U}{R}");

        // Magma Opus deals 4 damage divided as you choose among any number of targets. Tap two target permanents. Create a 4/4 blue and red Elemental creature token. Draw two cards.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(4).withChooseHint("damage"));
        this.getSpellAbility().addEffect(new MagmaOpusTapEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, StaticFilters.FILTER_PERMANENTS).withChooseHint("tap"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PrismariToken()));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("Draw two cards"));

        // {U/R}{U/R}, Discard Magma Opus: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new CreateTokenEffect(new TreasureToken()), new ManaCostsImpl("{U/R}{U/R}")
        );
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    private MagmaOpus(final MagmaOpus card) {
        super(card);
    }

    @Override
    public MagmaOpus copy() {
        return new MagmaOpus(this);
    }
}

class MagmaOpusTapEffect extends OneShotEffect {

    MagmaOpusTapEffect() {
        super(Outcome.Benefit);
        staticText = "Tap two target permanents.";
    }

    private MagmaOpusTapEffect(final MagmaOpusTapEffect effect) {
        super(effect);
    }

    @Override
    public MagmaOpusTapEffect copy() {
        return new MagmaOpusTapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            if (!source.getTargets().get(1).getTargets().contains(targetId)) {
                continue;
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.tap(source, game);
            }
        }
        return true;
    }
}
