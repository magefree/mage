package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Knife extends CardImpl {

    public Knife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.EQUIPMENT);

        // As long as it's your turn, equipped creature gets +1/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(1, 0), MyTurnCondition.instance,
                "as long as it's your turn, equipped creature gets +1/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ), MyTurnCondition.instance, "and has first strike"));
        this.addAbility(ability);

        // {2}, Sacrifice Knife: Draw a card.
        this.addAbility(new ClueAbility(true));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private Knife(final Knife card) {
        super(card);
    }

    @Override
    public Knife copy() {
        return new Knife(this);
    }
}
