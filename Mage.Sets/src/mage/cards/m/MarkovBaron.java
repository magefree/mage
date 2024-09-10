package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarkovBaron extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VAMPIRE, "Vampires");

    public MarkovBaron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Other Vampires you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Madness {2}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private MarkovBaron(final MarkovBaron card) {
        super(card);
    }

    @Override
    public MarkovBaron copy() {
        return new MarkovBaron(this);
    }
}
