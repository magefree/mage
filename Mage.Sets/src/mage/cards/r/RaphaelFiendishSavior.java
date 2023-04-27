package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreaturePutInYourGraveyardCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.DevilToken;
import mage.watchers.common.CreaturePutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelFiendishSavior extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Demons, Devils, Imps, and Tieflings");
    private static final FilterPermanent filter2 = new FilterPermanent();
    private static final Predicate<MageObject> predicate = Predicates.or(
            SubType.DEMON.getPredicate(),
            SubType.DEVIL.getPredicate(),
            SubType.IMP.getPredicate(),
            SubType.TIEFLING.getPredicate()
    );

    static {
        filter.add(predicate);
        filter2.add(predicate);
    }

    public RaphaelFiendishSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other Demons, Devils, Imps, and Tieflings you control get +1/+1 and have lifelink.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter2, true
        ).setText("and have lifelink"));
        this.addAbility(ability);

        // At the beginning of each end step, if a creature card was put into your graveyard from anywhere this turn, create a 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new DevilToken()), TargetController.ANY,
                CreaturePutInYourGraveyardCondition.instance, false
        ).addHint(CreaturePutInYourGraveyardCondition.getHint()), new CreaturePutIntoGraveyardWatcher());
    }

    private RaphaelFiendishSavior(final RaphaelFiendishSavior card) {
        super(card);
    }

    @Override
    public RaphaelFiendishSavior copy() {
        return new RaphaelFiendishSavior(this);
    }
}
