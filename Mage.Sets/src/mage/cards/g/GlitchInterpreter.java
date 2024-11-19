package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlitchInterpreter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control no face-down permanents");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("colorless creatures");

    static {
        filter.add(FaceDownPredicate.instance);
        filter2.add(ColorlessPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public GlitchInterpreter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Glitch Interpreter enters, if you control no face-down permanents, return Glitch Interpreter to its owner's hand and manifest dread.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandSourceEffect()).withInterveningIf(condition);
        ability.addEffect(new ManifestDreadEffect().concatBy("and"));
        this.addAbility(ability);

        // Whenever one or more colorless creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter2));
    }

    private GlitchInterpreter(final GlitchInterpreter card) {
        super(card);
    }

    @Override
    public GlitchInterpreter copy() {
        return new GlitchInterpreter(this);
    }
}
