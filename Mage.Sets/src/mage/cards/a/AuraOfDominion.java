package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AuraOfDominion extends CardImpl {

    public AuraOfDominion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Untap));
        this.addAbility(new EnchantAbility(auraTarget));

        Ability ability = new SimpleActivatedAbility(new UntapAttachedEffect(), new GenericManaCost(1));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
        this.addAbility(ability);
    }

    private AuraOfDominion(final AuraOfDominion card) {
        super(card);
    }

    @Override
    public AuraOfDominion copy() {
        return new AuraOfDominion(this);
    }

}
