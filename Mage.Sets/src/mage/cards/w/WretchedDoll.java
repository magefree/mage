package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WretchedDoll extends CardImpl {

    public WretchedDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.TOY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {B}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WretchedDoll(final WretchedDoll card) {
        super(card);
    }

    @Override
    public WretchedDoll copy() {
        return new WretchedDoll(this);
    }
}
