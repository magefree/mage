package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoetsQuill extends CardImpl {

    public PoetsQuill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Poet's Quill enters the battlefield, learn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LearnEffect())
                .addHint(OpenSideboardHint.instance));

        // Equipped creature gets +1/+1 and has lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has lifelink"));
        this.addAbility(ability);

        // Equip {1}{B}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{1}{B}"), false));
    }

    private PoetsQuill(final PoetsQuill card) {
        super(card);
    }

    @Override
    public PoetsQuill copy() {
        return new PoetsQuill(this);
    }
}
