package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EnchantmentOrEnchantedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyxHerald extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("enchanted creature or enchantment creature you control");

    static {
        filter.add(EnchantmentOrEnchantedPredicate.instance);
    }

    public NyxHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target enchanted creature or enchantment creature you control gets +1/+1 and gains trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(1, 1)
                        .setText("target enchanted creature or enchantment creature you control gets +1/+1"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private NyxHerald(final NyxHerald card) {
        super(card);
    }

    @Override
    public NyxHerald copy() {
        return new NyxHerald(this);
    }
}
