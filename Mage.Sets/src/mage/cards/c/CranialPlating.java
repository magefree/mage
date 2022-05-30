package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class CranialPlating extends CardImpl {

    public CranialPlating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(ArtifactYouControlCount.instance, StaticValue.get(0)))
                .addHint(ArtifactYouControlHint.instance));

        // {B}{B}: Attach Cranial Plating to target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature, "Attach {this} to target creature you control"), new ManaCostsImpl<>("{B}{B}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), false));
    }

    private CranialPlating(final CranialPlating card) {
        super(card);
    }

    @Override
    public CranialPlating copy() {
        return new CranialPlating(this);
    }
}
