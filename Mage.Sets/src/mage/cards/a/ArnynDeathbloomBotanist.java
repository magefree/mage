package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class ArnynDeathbloomBotanist extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control with power or toughness 1 or less");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(
            new PowerPredicate(ComparisonType.OR_LESS, 1),
            new ToughnessPredicate(ComparisonType.OR_LESS, 1)
        ));
    }

    public ArnynDeathbloomBotanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with power or toughness 1 or less dies, target opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesCreatureTriggeredAbility(new LoseLifeTargetEffect(2), false, filter);
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ArnynDeathbloomBotanist(final ArnynDeathbloomBotanist card) {
        super(card);
    }

    @Override
    public ArnynDeathbloomBotanist copy() {
        return new ArnynDeathbloomBotanist(this);
    }
}
