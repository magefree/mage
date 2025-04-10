package mage.cards.h;

import mage.ConditionalMana;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastConditionalMana;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HerdHeirloom extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public HerdHeirloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new HerdHeirloomManaBuilder()));

        // {T}: Until end of turn, target creature you control with power 4 or greater gains trample and "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("until end of turn, target creature you control with power 4 or greater gains trample"), new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)))
                .setText("and \"Whenever this creature deals combat damage to a player, draw a card.\""));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HerdHeirloom(final HerdHeirloom card) {
        super(card);
    }

    @Override
    public HerdHeirloom copy() {
        return new HerdHeirloom(this);
    }
}

class HerdHeirloomManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CreatureCastConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell";
    }
}
