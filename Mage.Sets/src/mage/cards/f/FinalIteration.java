
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.HumanWizardToken;

/**
 *
 * @author fireshoes
 */
public final class FinalIteration extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wizards");
    private static final FilterSpell filterSpell = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filterSpell.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public FinalIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wizards you control get +2/+1 and have flying.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 1, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("and have flying");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new HumanWizardToken()), filterSpell, false));
    }

    private FinalIteration(final FinalIteration card) {
        super(card);
    }

    @Override
    public FinalIteration copy() {
        return new FinalIteration(this);
    }
}
