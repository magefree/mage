
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class JorubaiMurkLurker extends CardImpl {

    private static final String rule = "{this} gets +1/+1 as long as you control a Swamp";
    private static final FilterLandPermanent filter = new FilterLandPermanent("a Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public JorubaiMurkLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.LEECH);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Jorubai Murk Lurker gets +1/+1 as long as you control a Swamp.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter), rule)));

        // {1}{B}: Target creature gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private JorubaiMurkLurker(final JorubaiMurkLurker card) {
        super(card);
    }

    @Override
    public JorubaiMurkLurker copy() {
        return new JorubaiMurkLurker(this);
    }
}
