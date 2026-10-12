package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnnieJoinsUp extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AnnieJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Annie Joins Up enters the battlefield, it deals 5 damage to target creature or planeswalker an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(5));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // If a triggered ability of a legendary creature you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY)));
    }

    private AnnieJoinsUp(final AnnieJoinsUp card) {
        super(card);
    }

    @Override
    public AnnieJoinsUp copy() {
        return new AnnieJoinsUp(this);
    }
}
