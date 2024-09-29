package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShuYunTheSilentTempest extends CardImpl {

    public ShuYunTheSilentTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());
        // Whenever you cast a noncreature spell, you may pay {R/W}{R/W}. If you do, target creature gains double strike until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DoIfCostPaid(
                        new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), 
                        new ManaCostsImpl<>("{R/W}{R/W}"),
                        "Pay to let target creature gain double strike?"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);


    }

    private ShuYunTheSilentTempest(final ShuYunTheSilentTempest card) {
        super(card);
    }

    @Override
    public ShuYunTheSilentTempest copy() {
        return new ShuYunTheSilentTempest(this);
    }
}
