package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class HauntedPlateMail extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control no creatures"), ComparisonType.EQUAL_TO, 0
    );

    public HauntedPlateMail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 4)));

        // {0}: Until end of turn, Haunted Plate Mail becomes a 4/4 Spirit artifact creature that's no longer an Equipment. Activate this ability only if you control no creatures.
        this.addAbility(new ActivateIfConditionActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        4, 4, "4/4 Spirit artifact " +
                        "creature that's no longer an Equipment", SubType.SPIRIT
                ).withType(CardType.ARTIFACT), CardType.ARTIFACT, Duration.EndOfTurn
        ).andNotEquipment(true).withDurationRuleAtStart(true), new GenericManaCost(0), condition));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{4}")));
    }

    private HauntedPlateMail(final HauntedPlateMail card) {
        super(card);
    }

    @Override
    public HauntedPlateMail copy() {
        return new HauntedPlateMail(this);
    }
}
