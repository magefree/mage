package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 * @author balazskristof
 */
public final class StiltzkinMoogleMerchant extends CardImpl {

    public StiltzkinMoogleMerchant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOOGLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {2}, {T}: Target opponent gains control of another target permanent you control. If they do, you draw a card.
        Ability ability = new SimpleActivatedAbility(new StiltzkinMoogleMerchantEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT));
        this.addAbility(ability);
    }

    private StiltzkinMoogleMerchant(final StiltzkinMoogleMerchant card) {
        super(card);
    }

    @Override
    public StiltzkinMoogleMerchant copy() {
        return new StiltzkinMoogleMerchant(this);
    }
}

class StiltzkinMoogleMerchantEffect extends OneShotEffect {

    StiltzkinMoogleMerchantEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent gains control of another target permanent you control. If they do, you draw a card.";
    }

    private StiltzkinMoogleMerchantEffect(StiltzkinMoogleMerchantEffect effect) {
        super(effect);
    }

    @Override
    public StiltzkinMoogleMerchantEffect copy() {
        return new StiltzkinMoogleMerchantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent == null) {
            return false;
        }
        UUID opponent = getTargetPointer().getFirst(game, source);
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, opponent
        ).setTargetPointer(new FixedTarget(permanent.getId(), game)), source);
        game.processAction();
        if (permanent.isControlledBy(opponent)) {
            new DrawCardSourceControllerEffect(1).apply(game, source);
            return true;
        }
        return false;
    }
}

