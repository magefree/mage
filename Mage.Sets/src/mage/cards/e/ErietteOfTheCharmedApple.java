package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedBySourceControllerPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Xanderhall
 */
public final class ErietteOfTheCharmedApple extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature that's enchanted by an Aura you control");
    private static final DynamicValue count = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.AURA));

    static {
        filter.add(EnchantedBySourceControllerPredicate.instance);   
    }

    public ErietteOfTheCharmedApple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Each creature that's enchanted by an Aura you control can't attack you or planeswalkers you control.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouAllEffect(Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of your end step, each opponent loses X life and you gain X life, where X is the number of Auras you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new LoseLifeOpponentsEffect(count)
                .setText("each opponent loses X life"), TargetController.YOU, false);
        ability.addEffect(new GainLifeEffect(count)
                .setText("and you gain X life, where X is the number of Auras you control"));
        ability.addHint(new ValueHint("Number of Auras you control", count));
        this.addAbility(ability);
    }

    private ErietteOfTheCharmedApple(final ErietteOfTheCharmedApple card) {
        super(card);
    }

    @Override
    public ErietteOfTheCharmedApple copy() {
        return new ErietteOfTheCharmedApple(this);
    }
}
