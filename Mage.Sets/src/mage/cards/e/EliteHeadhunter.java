package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EliteHeadhunter extends CardImpl {

    public EliteHeadhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/R}{B/R}{B/R}{B/R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // {B/R}{B/R}{B/R}, Sacrifice another creature or an artifact: Elite Headhunter deals 2 damage to target creature or planeswalker.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2), new ManaCostsImpl<>("{B/R}{B/R}{B/R}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_OTHER_CREATURE));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private EliteHeadhunter(final EliteHeadhunter card) {
        super(card);
    }

    @Override
    public EliteHeadhunter copy() {
        return new EliteHeadhunter(this);
    }
}
