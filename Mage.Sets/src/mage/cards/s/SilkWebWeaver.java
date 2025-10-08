package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanCitizenToken;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class SilkWebWeaver extends CardImpl {

    public SilkWebWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Web-slinging {1}{G}{W}
        this.addAbility(new WebSlingingAbility(this, "{1}{G}{W}"));

        // Whenever you cast a creature spell, create a 1/1 green and white Human Citizen creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new HumanCitizenToken()),
                StaticFilters.FILTER_SPELL_A_CREATURE,
                false
        ));

        // {3}{G}{W}: Creatures you control get +2/+2 and gain vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostControlledEffect(2, 2, Duration.EndOfTurn).setText("Creatures you control get +2/+2"),
                new ManaCostsImpl<>("{3}{G}{W}")
        );
        ability.addEffect(
                new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("gain vigilance until end of turn")
                .concatBy("and")
        );
        this.addAbility(ability);
    }

    private SilkWebWeaver(final SilkWebWeaver card) {
        super(card);
    }

    @Override
    public SilkWebWeaver copy() {
        return new SilkWebWeaver(this);
    }
}
