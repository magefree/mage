package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HaveInitiativeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RasaadYnBashir extends CardImpl {

    public RasaadYnBashir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        )));

        // Whenever Rasaad yn Bashir attacks, if you have the initiative, double the toughness of each creature you control until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new RasaadYnBashirEffect()),
                HaveInitiativeCondition.instance, "Whenever {this} attacks, if you have the initiative, " +
                "double the toughness of each creature you control until end of turn."
        ));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private RasaadYnBashir(final RasaadYnBashir card) {
        super(card);
    }

    @Override
    public RasaadYnBashir copy() {
        return new RasaadYnBashir(this);
    }
}

class RasaadYnBashirEffect extends OneShotEffect {

    RasaadYnBashirEffect() {
        super(Outcome.Benefit);
    }

    private RasaadYnBashirEffect(final RasaadYnBashirEffect effect) {
        super(effect);
    }

    @Override
    public RasaadYnBashirEffect copy() {
        return new RasaadYnBashirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (permanent.getToughness().getValue() != 0) {
                game.addEffect(new BoostTargetEffect(
                        0, permanent.getToughness().getValue()
                ).setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }
        return true;
    }
}
