package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SonorousHowlbonder extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with menace");

    static {
        filter.add(new AbilityPredicate(MenaceAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SonorousHowlbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/R}{B/R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Each creature you control with menace can't be blocked except by three or more creatures.
        ContinuousEffect effect = new CantBeBlockedByOneAllEffect(3, filter);
        effect.setDependedToType(DependencyType.AddingAbility);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private SonorousHowlbonder(final SonorousHowlbonder card) {
        super(card);
    }

    @Override
    public SonorousHowlbonder copy() {
        return new SonorousHowlbonder(this);
    }
}
