package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.PlaneswalkEffect;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Skiwkr
 */
public final class TARDIS extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.TIME_LORD, "you control a Time Lord")
    );

    private static final Hint hint = new ConditionHint(condition);

    public TARDIS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());

        // Whenever TARDIS attacks, if you control a Time Lord, the next spell you cast this turn has cascade and you may planeswalk.
        Ability ability = new AttacksTriggeredAbility(
                new NextSpellCastHasAbilityEffect(new CascadeAbility())
        ).withInterveningIf(condition);
        ability.addEffect(new PlaneswalkEffect(true).concatBy("and"));
        this.addAbility(ability.addHint(hint));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private TARDIS(final TARDIS card) {
        super(card);
    }

    @Override
    public TARDIS copy() {
        return new TARDIS(this);
    }
}
