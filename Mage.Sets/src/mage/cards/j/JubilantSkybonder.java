package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JubilantSkybonder extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public JubilantSkybonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/U}{W/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures you control with flying have "Spells your opponents cast that target this creature cost {2} more to cast."
        Ability gainAbility = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostModificationThatTargetSourceEffect(2, new FilterCard("Spells"), TargetController.OPPONENT)
                        .withTargetName("this creature")
        );

        ContinuousEffect effect = new GainAbilityAllEffect(gainAbility, Duration.WhileOnBattlefield, filter).withForceQuotes();
        effect.setDependedToType(DependencyType.AddingAbility);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private JubilantSkybonder(final JubilantSkybonder card) {
        super(card);
    }

    @Override
    public JubilantSkybonder copy() {
        return new JubilantSkybonder(this);
    }
}
