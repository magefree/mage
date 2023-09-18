package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class SunbathingRootwalla extends CardImpl {

    public SunbathingRootwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Domain -- {3}{G}: Until end of turn, Sunbathing Rootwalla gets +1/+1 for each basic land type among lands you control. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn, true),
                new ManaCostsImpl<>("{3}{G}")
        );
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.addHint(DomainHint.instance);
        this.addAbility(ability);
    }

    private SunbathingRootwalla(final SunbathingRootwalla card) {
        super(card);
    }

    @Override
    public SunbathingRootwalla copy() {
        return new SunbathingRootwalla(this);
    }
}
