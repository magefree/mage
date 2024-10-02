package mage.cards.i;

import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.AbilitySourceAttachedPredicate;
import mage.filter.predicate.other.NotManaAbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IllusionistsBracers extends CardImpl {

    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability of equipped creature");

    static {
        filter.add(NotManaAbilityPredicate.instance);
        filter.add(AbilitySourceAttachedPredicate.instance);
    }
    public IllusionistsBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new CopyStackObjectEffect(), filter, SetTargetPointer.SPELL)
                .setTriggerPhrase("Whenever an ability of equipped creature is activated, if it isn't a mana ability, "));

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private IllusionistsBracers(final IllusionistsBracers card) {
        super(card);
    }

    @Override
    public IllusionistsBracers copy() {
        return new IllusionistsBracers(this);
    }
}
