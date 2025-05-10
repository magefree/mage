package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.WizardBlackToken;

/**
 * @author balazskristof
 */
public final class KujaGenomeSorcerer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WIZARD, "Wizards");

    public KujaGenomeSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.secondSideCardClazz = mage.cards.t.TranceKujaFateDefied.class;

        // At the beginning of your end step, create a tapped 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent.", Then if you control four or more Wizards, transform Kuja.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new WizardBlackToken(), 1, true)
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 4),
                "if you control four or more Wizards, transform {this}"
        ).concatBy("Then"));
        this.addAbility(ability);
        this.addAbility(new TransformAbility());
    }

    private KujaGenomeSorcerer(final KujaGenomeSorcerer card) {
        super(card);
    }

    @Override
    public KujaGenomeSorcerer copy() {
        return new KujaGenomeSorcerer(this);
    }
}
