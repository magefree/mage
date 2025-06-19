
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.VampireToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BloodlineKeeper extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "you control five or more Vampires");
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4);
    private static final Hint hint = new ValueHint("Vampires you control", new PermanentsOnBattlefieldCount(filter));

    public BloodlineKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.l.LordOfLineage.class;

        this.addAbility(FlyingAbility.getInstance());

        // {T}: Create a 2/2 black Vampire creature token with flying.
        this.addAbility(new SimpleActivatedAbility(new CreateTokenEffect(new VampireToken()), new TapSourceCost()));

        // {B}: Transform Bloodline Keeper. Activate this ability only if you control five or more Vampires.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{B}"), condition
        ).addHint(hint));
    }

    private BloodlineKeeper(final BloodlineKeeper card) {
        super(card);
    }

    @Override
    public BloodlineKeeper copy() {
        return new BloodlineKeeper(this);
    }
}
