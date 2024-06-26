package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EquippedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HemlockVial extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        EquippedPredicate.instance,
                        CardType.CREATURE.getPredicate()
                ), SubType.EQUIPMENT.getPredicate()
        ));
    }

    public HemlockVial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        // When Hemlock Vial enters the battlefield, you draw a card and you lose 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {B}, {T}, Sacrifice Hemlock Vial: Each equipped creature and Equipment you control gains deathtouch until end of turn.
        ability = new SimpleActivatedAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter
        ), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HemlockVial(final HemlockVial card) {
        super(card);
    }

    @Override
    public HemlockVial copy() {
        return new HemlockVial(this);
    }
}
