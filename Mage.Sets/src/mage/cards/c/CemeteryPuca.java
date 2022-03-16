package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author jeffwadsworth
 */
public final class CemeteryPuca extends CardImpl {

    public CemeteryPuca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/B}{U/B}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever a creature dies, you may pay {1}. If you do, Cemetery Puca becomes a copy of that creature, except it has this ability.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DoIfCostPaid(new CemeteryPucaEffect(), new ManaCostsImpl("{1}")),
                false,
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                true));
    }

    private CemeteryPuca(final CemeteryPuca card) {
        super(card);
    }

    @Override
    public CemeteryPuca copy() {
        return new CemeteryPuca(this);
    }
}

class CemeteryPucaEffect extends OneShotEffect {

    public CemeteryPucaEffect() {
        super(Outcome.Copy);
        staticText = "{this} becomes a copy of that creature, except it has this ability";
    }

    public CemeteryPucaEffect(final CemeteryPucaEffect effect) {
        super(effect);
    }

    @Override
    public CemeteryPucaEffect copy() {
        return new CemeteryPucaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copyToCreature = game.getPermanent(source.getSourceId());
        if (copyToCreature != null) {
            Permanent copyFromCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (copyFromCreature != null) {
                game.copyPermanent(Duration.WhileOnBattlefield, copyFromCreature, copyToCreature.getId(), source, new EmptyCopyApplier());
                ContinuousEffect effect = new GainAbilityTargetEffect(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new CemeteryPucaEffect(), new ManaCostsImpl("{1}")), false, StaticFilters.FILTER_PERMANENT_A_CREATURE, true), Duration.WhileOnBattlefield);
                effect.setTargetPointer(new FixedTarget(copyToCreature.getId(), game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
