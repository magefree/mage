package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorcererOfTheFang extends CardImpl {

    public SorcererOfTheFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {5}{B}, {T}: Sorcerer of the Fang deals 2 damage to target opponent or planeswalker.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{5}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private SorcererOfTheFang(final SorcererOfTheFang card) {
        super(card);
    }

    @Override
    public SorcererOfTheFang copy() {
        return new SorcererOfTheFang(this);
    }
}
