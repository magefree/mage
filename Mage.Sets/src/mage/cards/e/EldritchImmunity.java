package mage.cards.e;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EldritchImmunity extends CardImpl {

    private static final FilterCard filter = new FilterCard("each color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public EldritchImmunity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.INSTANT}, "{C}");

        this.subtype.add(SubType.ELDRAZI);

        // Target creature you control gains protection from each color until end of turn.
        // Overload {4}{C}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{4}{C}"),
                new TargetControlledCreaturePermanent(), new GainAbilityTargetEffect(new ProtectionAbility(filter)));
    }

    private EldritchImmunity(final EldritchImmunity card) {
        super(card);
    }

    @Override
    public EldritchImmunity copy() {
        return new EldritchImmunity(this);
    }
}
