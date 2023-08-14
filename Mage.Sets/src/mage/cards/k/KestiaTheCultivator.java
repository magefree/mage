package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author TheElk801
 */
public final class KestiaTheCultivator extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("an enchanted creature or enchantment creature you control");

    static {
        filter.add(Predicates.or(
                EnchantedPredicate.instance,
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public KestiaTheCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Bestow {3}{G}{W}{U}
        this.addAbility(new BestowAbility(this, "{3}{G}{W}{U}"));

        // Enchanted creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEnchantedEffect(4, 4, Duration.WhileOnBattlefield)
        ));

        // Whenever an enchanted creature or enchantment creature you control attacks, draw a card.
        this.addAbility(new AttacksAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                false, filter, SetTargetPointer.NONE, false
        ));
    }

    private KestiaTheCultivator(final KestiaTheCultivator card) {
        super(card);
    }

    @Override
    public KestiaTheCultivator copy() {
        return new KestiaTheCultivator(this);
    }
}
