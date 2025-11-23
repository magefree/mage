package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JooDeeOneOfMany extends CardImpl {

    public JooDeeOneOfMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}, {T}: Surveil 1. Create a token that's a copy of this creature, then sacrifice an artifact or creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new SurveilEffect(1, false), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new CreateTokenCopySourceEffect());
        ability.addEffect(new SacrificeControllerEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE, 1, ", then"
        ));
        this.addAbility(ability);
    }

    private JooDeeOneOfMany(final JooDeeOneOfMany card) {
        super(card);
    }

    @Override
    public JooDeeOneOfMany copy() {
        return new JooDeeOneOfMany(this);
    }
}
