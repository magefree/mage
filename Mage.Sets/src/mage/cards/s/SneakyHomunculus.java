
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author dustinconrad
 */
public final class SneakyHomunculus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public SneakyHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sneaky Homunculus can't block or be blocked by creatures with power 2 or greater.
        Effect effect = new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield);
        effect.setText("{this} can't block");
        Ability ability = new SimpleEvasionAbility(effect);
        effect = new CantBlockCreaturesSourceEffect(filter);
        effect.setText("or be blocked by creatures with power 2 or greater");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SneakyHomunculus(final SneakyHomunculus card) {
        super(card);
    }

    @Override
    public SneakyHomunculus copy() {
        return new SneakyHomunculus(this);
    }
}
