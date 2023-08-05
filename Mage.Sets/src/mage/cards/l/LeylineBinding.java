package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineBinding extends CardImpl {

    public LeylineBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Domain -- This spell costs {1} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(DomainValue.REGULAR)
                .setText("This spell costs {1} less to cast for each basic land type among lands you control")
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));

        // When Leyline Binding enters the battlefield, exile target nonland permanent an opponent controls until Leyline Binding leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private LeylineBinding(final LeylineBinding card) {
        super(card);
    }

    @Override
    public LeylineBinding copy() {
        return new LeylineBinding(this);
    }
}
