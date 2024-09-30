package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InsidiousFungus extends CardImpl {

    public InsidiousFungus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}, Sacrifice Insidious Fungus: Choose one --
        // * Destroy target artifact.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent());

        // * Destroy target enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Draw a card. Then you may put a land card from your hand onto the battlefield tapped.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1))
                .addEffect(new PutCardFromHandOntoBattlefieldEffect(
                        StaticFilters.FILTER_CARD_LAND_A, false, true
                ).concatBy("Then")));
        this.addAbility(ability);
    }

    private InsidiousFungus(final InsidiousFungus card) {
        super(card);
    }

    @Override
    public InsidiousFungus copy() {
        return new InsidiousFungus(this);
    }
}
