
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class CopperGnomes extends CardImpl {

    public CopperGnomes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {4}, Sacrifice Copper Gnomes: You may put an artifact card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_ARTIFACT_AN),
                new ManaCostsImpl<>("{4}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CopperGnomes(final CopperGnomes card) {
        super(card);
    }

    @Override
    public CopperGnomes copy() {
        return new CopperGnomes(this);
    }
}
