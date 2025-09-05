package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanWizardToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FinalIteration extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Wizards");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public FinalIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wizards you control get +2/+1 and have flying.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                2, 1, Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have flying"));
        this.addAbility(ability);

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue Human Wizard creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanWizardToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));
    }

    private FinalIteration(final FinalIteration card) {
        super(card);
    }

    @Override
    public FinalIteration copy() {
        return new FinalIteration(this);
    }
}
