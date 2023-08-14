
package mage.cards.n;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetSpell;

/**
 * @author JRHerlehy
 *         Created on 4/5/18.
 */
public final class NaruMehaMasterWizard extends CardImpl {

    private static final FilterSpell spellFilter = new FilterSpell("instant or sorcery spell you control");
    private static final FilterCreaturePermanent wizardFilter = new FilterCreaturePermanent(SubType.WIZARD, "Wizards");

    static {
        wizardFilter.add(TargetController.YOU.getControllerPredicate());
        spellFilter.add(TargetController.YOU.getControllerPredicate());
        spellFilter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public NaruMehaMasterWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        //Flash
        this.addAbility(FlashAbility.getInstance());

        //When Naru Meha, Master Wizard enters the battlefield, copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CopyTargetSpellEffect());
        ability.addTarget(new TargetSpell(spellFilter));
        this.addAbility(ability);

        //Other Wizards you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, wizardFilter, true)));
    }

    private NaruMehaMasterWizard(final NaruMehaMasterWizard card) {
        super(card);
    }

    @Override
    public NaruMehaMasterWizard copy() {
        return new NaruMehaMasterWizard(this);
    }

}
