package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.AttachmentType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import org.checkerframework.checker.units.qual.A;

/**
 * @author TheElk801
 */
public final class HexgoldHalberd extends CardImpl {

    public HexgoldHalberd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // As long as it's your turn, equipped creature has first strike and trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "as long as it's your turn, equipped creature has first strike"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "and trample"
        ));
        this.addAbility(ability.addHint(MyTurnHint.instance));

        // Equip {2}{R}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{R}")));
    }

    private HexgoldHalberd(final HexgoldHalberd card) {
        super(card);
    }

    @Override
    public HexgoldHalberd copy() {
        return new HexgoldHalberd(this);
    }
}
