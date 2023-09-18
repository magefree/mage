package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author weirddan455
 */
public final class SurvivorOfKorlis extends CardImpl {

    public SurvivorOfKorlis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {1}{W}, Exile Survivor of Korlis from your graveyard: Scry 2.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new ScryEffect(2, false), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SurvivorOfKorlis(final SurvivorOfKorlis card) {
        super(card);
    }

    @Override
    public SurvivorOfKorlis copy() {
        return new SurvivorOfKorlis(this);
    }
}
