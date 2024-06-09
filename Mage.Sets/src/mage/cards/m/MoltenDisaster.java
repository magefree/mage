package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MoltenDisaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    private static final String rule = "if this spell was kicked, it has split second. " +
            "<i>(As long as this spell is on the stack, players can't cast spells or activate abilities that aren't mana abilities.)</i>";

    public MoltenDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // If Molten Disaster was kicked, it has split second.
        Ability ability = new SimpleStaticAbility(Zone.STACK, SplitSecondAbility.getSplitSecondEffectWithCondition(KickedCondition.ONCE)
                .setText(rule));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));

        // Molten Disaster deals X damage to each creature without flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(ManacostVariableValue.REGULAR, filter));
    }

    private MoltenDisaster(final MoltenDisaster card) {
        super(card);
    }

    @Override
    public MoltenDisaster copy() {
        return new MoltenDisaster(this);
    }
}
