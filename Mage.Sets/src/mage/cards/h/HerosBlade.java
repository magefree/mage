package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HerosBlade extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HerosBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 2)));

        // Whenever a legendary creature enters the battlefield under your control, you may attach Hero's Blade to it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4)));
    }

    private HerosBlade(final HerosBlade card) {
        super(card);
    }

    @Override
    public HerosBlade copy() {
        return new HerosBlade(this);
    }
}
