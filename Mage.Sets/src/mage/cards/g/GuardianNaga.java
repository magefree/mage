package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianNaga extends AdventureCard {

    public GuardianNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{5}{W}{W}", "Banishing Coils", "{2}{W}");

        this.subtype.add(SubType.NAGA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // As long as it's your turn, prevent all damage that would be dealt to Guardian Naga.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield), MyTurnCondition.instance,
                "as long as it's your turn, prevent all damage that would be dealt to {this}"
        )));

        // Banishing Coils
        // Exile target artifact or enchantment.
        this.getSpellCard().getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private GuardianNaga(final GuardianNaga card) {
        super(card);
    }

    @Override
    public GuardianNaga copy() {
        return new GuardianNaga(this);
    }
}
