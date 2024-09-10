package mage.cards.u;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltraMagnusArmoredCarrier extends CardImpl {

    public UltraMagnusArmoredCarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(7);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Formidable -- Whenever Ultra Magnus attacks, attacking creatures you control gain indestructible until end of turn. If those creatures have total power 8 or greater, convert Ultra Magnus.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), UltraMagnusArmoredCarrierCondition.instance,
                "If those creatures have total power 8 or greater, convert {this}"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private UltraMagnusArmoredCarrier(final UltraMagnusArmoredCarrier card) {
        super(card);
    }

    @Override
    public UltraMagnusArmoredCarrier copy() {
        return new UltraMagnusArmoredCarrier(this);
    }
}

enum UltraMagnusArmoredCarrierCondition implements Condition {
    instance;
    private static final FilterPermanent filter = new FilterAttackingCreature();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum() >= 8;
    }
}